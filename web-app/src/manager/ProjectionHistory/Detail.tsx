import React from 'react';
import { Button, Image } from 'antd';
import { converDate, converTime} from '../../components/FuctionGlobal';
import { useNavigate } from 'react-router-dom';

interface IDetail {
    data: any
}

const Detail: React.FC<IDetail> = ({data}) => {
    const navigate = useNavigate()

    if(data.length === 0) return <></>
    
    const urlImg = data.thumnails[0]
    return (
        <div className='Detail'>
            <div className='left_Detail'>
                <Image
                    width={250}
                    src={urlImg}
                />
                <div>
                    <span style={{fontWeight: "bold", fontSize: "1.1rem"}}>Tên phim: {data.filmName}</span>
                    <span>Ngày chiếu: {converDate(data.startTime)}</span>
                    <span>Giờ bắt đầu: {converTime(data.startTime)}</span>
                    <span>Dự kiến kết thúc: {converTime(data.endTime)}</span>
                    <span>Phòng chiếu:{data.roomName}</span>
                </div>
            </div>

            <div className='right_Detail'>
                <div>Còn lại: {`${data.availables}/${data.totalSeats}`}</div>
                <div><Button
                     onClick={() => {
                        navigate(`/admin/projection-history/view-all-booking/${data.id}`)
                    }}
                >Xem vé đã đặt</Button></div>
            </div>
        </div>
    )
}

export default Detail