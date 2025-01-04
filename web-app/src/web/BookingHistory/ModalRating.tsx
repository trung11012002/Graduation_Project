import React, { useContext, useState } from 'react'
import { Button, Modal, Rate } from 'antd'
import TextArea from 'antd/es/input/TextArea'
import { rateMovie } from '../../apis/movie'
import { AuthContextProvider } from '../../contexts/AuthContext'
import {MessageContextProvider} from "../../contexts/MessageContext";

interface IModalRating {
    open: boolean,
    setOpen: (open: boolean) => void
    movie: any
    getHistoryBooking: any
}

const ModalRating: React.FC<IModalRating> = ({ open, setOpen, movie, getHistoryBooking }) => {

    const auth = useContext(AuthContextProvider)
    const user = auth?.userState.user

    const [valueRate, setValueRate] = useState<number>(0)
    const [value, setValue] = useState<string>("")

    const mess = useContext(MessageContextProvider);
    const success = mess?.success;
    const error = mess?.error;

    const handleOk = async() => {
        const data = {
            filmId: Number(movie.filmResponse.id),
            userId: Number(user?.id),
            star: valueRate,
            comment: value
        }
        const res = await rateMovie(data)
        if(res?.code === 1000) {
            setOpen(false);
            getHistoryBooking();
            success("Đánh giá thành công");
        } else {
            error("Đánh giá thất bại");
        }
    };

    const handleCancel = () => {
        setOpen(false);
    };

    return (
        <Modal
            // width={700}
            style={{}}
            open={open}
            title={`Đánh giá phim ${movie.filmResponse.name}`}
            onOk={handleOk}
            onCancel={handleCancel}
            footer={[
                <Button key="back" onClick={handleCancel}>
                    Trở lại
                </Button>,
                <Button key="submit" type="primary" onClick={handleOk}>
                    Đánh giá
                </Button>,
            ]}
        >
            <div className='movie-quality'>
                <span>Đánh giá chất lượng:</span>
                <Rate
                    style={{ fontSize: 39 }}
                    count={10}
                    value={valueRate}
                    onChange={setValueRate}
                />
            </div>

            <div>
                Nội dung đánh giá:
                <TextArea
                    rows={4}
                    onChange={(e: any) => {
                        setValue(e.target.value)
                    }}
                />
            </div>
        </Modal>
    )
}

export default ModalRating
