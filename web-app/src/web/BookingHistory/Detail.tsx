import { Button, Image } from 'antd';
import React, { useState } from 'react';
import { converDate, converTime, formatVNDCurrency } from '../../components/FuctionGlobal';
import ModalRating from './ModalRating';
import DetailBooking from './DetailBooking';
import { StarOutlined, InfoCircleOutlined } from '@ant-design/icons';  // Import icon
import "./Detail.css";
interface IDetail {
    movie: any;
    getHistoryBooking: any;
}

const Detail: React.FC<IDetail> = ({ movie, getHistoryBooking }) => {

    const [openRated, setOpenRated] = useState<boolean>(false);
    const [openDetail, setOpenDetail] = useState<boolean>(false);

    const urlImg = movie?.filmResponse?.thumnails[0];
    const type = movie.filmResponse.typeNames;

    const openModalRating = () => {
        setOpenRated(true);
    };

    return (
        <div className='Detail'>
            <div className='left_Detail'>
                <Image
                    width={250}
                    src={urlImg}
                />
                <div>
                    <span>Tên phim: {movie.filmResponse.name}</span>
                    <span>Thể loại: {type.map((value: any, index: number) => <>{value}{type.length - 1 === index ? "" : ", "}</>)}</span>
                    <span>Rạp chiếu: {movie.cinema.name}</span>
                    <span>Thời gian đặt: {converDate(movie.timeBooking)} {converTime(movie.timeBooking)}</span>
                    <span>Thanh toán: {formatVNDCurrency(movie.totalPrice)}</span>
                </div>
            </div>

            <div className='right_Detail'>
                <Button
                    type="primary"
                    shape="round"
                    onClick={() => setOpenDetail(true)}
                    className="button-detail"
                >
                    Xem chi tiết
                </Button>

                {!movie.rated ? (
                    <Button
                        type="dashed"
                        shape="round"
                        onClick={openModalRating}
                        className="button-rating"
                    >
                        Đánh giá ngay
                    </Button>
                ) : (
                    <div className='success-booking-history'>
                        <img src="https://pixlok.com/wp-content/uploads/2021/12/Green-Tick-Icon-SVG-03vd.png" alt="" />
                        <span>Đã đánh giá</span>
                    </div>
                )}
            </div>

            {openRated &&
                <ModalRating
                    open={openRated}
                    setOpen={setOpenRated}
                    movie={movie}
                    getHistoryBooking={getHistoryBooking}
                />
            }

            {openDetail &&
                <DetailBooking
                    open={openDetail}
                    setOpen={setOpenDetail}
                    movie={movie}
                />
            }
        </div>
    );
};

export default Detail;
