import React, { useContext, useEffect, useState } from 'react';
import './index.css';
import HeaderWeb from '../../components/Header/HeaderWeb';
import { Button } from 'antd';
import { useParams } from 'react-router-dom';
import { getSeatsStatus } from '../../apis/theater';
import { converDate, converTime, formatVNDCurrency } from '../../components/FuctionGlobal';
import ConfirmBook from './ConfirmBook';
import { createPayment } from '../../apis/payment';
import { AuthContextProvider } from '../../contexts/AuthContext';

interface SeatProps {
  id: string;
  isBooked: boolean;
  isSelected: boolean;
  isVip: boolean;
  onClick: () => void;
}

const Seat: React.FC<SeatProps> = ({ id, isBooked, isSelected, isVip, onClick }) => {
  const getSeatClass = () => {
    if (isBooked) return 'seat seat-booked';
    if (isSelected) return 'seat seat-selected';
    if (isVip) return 'seat seat-vip';
    return 'seat seat-normal';
  };

  return (
      <div className={getSeatClass()} onClick={!isBooked ? onClick : undefined}>
        <span className="seat-label">{id}</span>
      </div>
  );
};

const ChooseSeats: React.FC = () => {
  const { scheduleId } = useParams();
  const [tickets, setTickets] = useState<any>();
  const [checkActive, setCheckActive] = useState<string[]>([])
  const [open, setOpen] = useState(false);
  const [dataTable, setDataTable] = useState<any>([]);
  const [url, setUrl] = useState<string>("");

  const auth = useContext(AuthContextProvider);
  const user = auth?.userState.user;

  const calculateTotalPrice = () => {
    return checkActive.reduce((sum, seat) => {
      const rowNumber = Number(seat.split("-")[0]);
      return sum + (rowNumber < 5 ? 50000 : 80000);
    }, 0);
  };

  const handleSeatClick = (seatId: string) => {
    setCheckActive(prev =>
        prev.includes(seatId)
            ? prev.filter(id => id !== seatId)
            : [...prev, seatId]
    );
  };

  const confirmBook = async () => {
    const totalPrice = calculateTotalPrice();
    const res = await createPayment({ amount: totalPrice });
    if (res?.code === 1000) {
      setUrl(res.data);
      localStorage.setItem('checkActive', `${checkActive}`)
      localStorage.setItem('scheduleId', `${scheduleId}`)
      localStorage.setItem('userId', `${user?.id}`)
    }
    setOpen(true);
  };

  useEffect(() => {
    const updateDataTable = () => {
      const newData = checkActive.map((seat, index) => ({
        key: index,
        chair: seat,
        type: Number(seat.split("-")[0]) < 5 ? "ghế thường" : "ghế VIP",
        price: Number(seat.split("-")[0]) < 5 ? "50.000VND" : "80.000VND"
      }));
      setDataTable(newData);
    };
    updateDataTable();
  }, [checkActive]);

  useEffect(() => {
    const fetchSeatsStatus = async () => {
      if (scheduleId) {
        const res = await getSeatsStatus({ scheduleId: Number(scheduleId) });
        if (res?.code === 200) {
          setTickets(res.data);
        }
      }
    };
    fetchSeatsStatus();
  }, [scheduleId]);

  return (
      <div className='choose-seats-container'>
        <HeaderWeb />

        <div className='content-wrapper'>
          <h1 className='title-ChooseSeats'>Đặt vé xem phim</h1>

          {tickets && (
              <div className='content-ChooseSeats'>
                <div className='seats-section'>
                  <div className='screen-BookTickets'>
                    <span>MÀN HÌNH CHIẾU</span>
                  </div>

                  <div className='seats-container'>
                    {Array.from({ length: tickets.row }, (_, i) => (
                        Array.from({ length: tickets.column }, (_, j) => {
                          const seatId = `${i + 1}-${j + 1}`;
                          return (
                              <Seat
                                  key={seatId}
                                  id={seatId}
                                  isBooked={tickets.bookedSeats.includes(seatId)}
                                  isSelected={checkActive.includes(seatId)}
                                  isVip={i + 1 >= 5}
                                  onClick={() => handleSeatClick(seatId)}
                              />
                          );
                        })
                    ))}
                  </div>

                  <div className='note-BookTickets'>
                    <div className='note-item'>
                      <div className='note-color vip'></div>
                      <span>Ghế VIP</span>
                    </div>
                    <div className='note-item'>
                      <div className='note-color normal'></div>
                      <span>Ghế thường</span>
                    </div>
                    <div className='note-item'>
                      <div className='note-color booked'></div>
                      <span>Ghế đã đặt</span>
                    </div>
                    <div className='note-item'>
                      <div className='note-color selected'></div>
                      <span>Ghế đang chọn</span>
                    </div>
                  </div>
                </div>

                <div className="booking-info">
                  <div className="movie-details">
                    <h3>Thông tin đặt vé</h3>
                    <p>Phim: {tickets.scheduleResponse.filmName}</p>
                    <p>Phòng chiếu: {tickets.scheduleResponse.roomName}</p>
                    <p>Thời gian: {converTime(tickets.scheduleResponse.startTime)} - {converDate(tickets.scheduleResponse.startTime)}</p>
                    <p>Ghế: {checkActive.join(", ")}</p>
                  </div>
                  <div className="price-summary">
                    <p>Tổng tiền: {formatVNDCurrency(calculateTotalPrice())}</p>
                  </div>
                  <Button
                      type="primary"
                      className="btn-continue"
                      onClick={confirmBook}
                      disabled={checkActive.length === 0}
                  >
                    Tiếp tục
                  </Button>
                </div>
              </div>
          )}

          {open && (
              <ConfirmBook
                  open={open}
                  setOpen={setOpen}
                  dataTable={dataTable}
                  sumPrice={calculateTotalPrice()}
                  url={url}
              />
          )}
        </div>
      </div>
  );
};

export default ChooseSeats;