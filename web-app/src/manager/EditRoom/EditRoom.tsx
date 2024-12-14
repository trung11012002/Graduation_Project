import React, { useContext, useEffect, useState } from 'react';
import { Button, Form, Input, InputNumber } from 'antd';
import { useNavigate, useParams } from 'react-router-dom';
import { getRoomDetails, updateRoom } from '../../apis/theater';
import { MessageContextProvider } from '../../contexts/MessageContext';
import { MoviesContextProvider } from '../../contexts/Movies';

const EditRoom = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const mess = useContext(MessageContextProvider);
    const success = mess?.success;
    const error = mess?.error;

    const moviesContext = useContext(MoviesContextProvider);
    const findCinema = moviesContext?.findCinema;
    const cinema = moviesContext?.movies?.cinema;

    const [room, setRoom] = useState<any>(null);

    const initialValues = {
        ...room,
    };

    const fetchRoomDetails = async () => {
        try {
            const res = await getRoomDetails({ id: Number(id) });
            if (res?.code === 1000) {
                setRoom(res.data);
            } else {
                error(res?.msg);
            }
        } catch (err) {
            error("Không thể tải thông tin phòng");
        }
    };

    const onFinish = async (values: any) => {
        const {name, horizontalSeats, verticalSeats} = values;
        const req = {
            id: Number(id),
            name,
            horizontalSeats,
            verticalSeats,
            cinemaId: cinema?.id,
        };

        try {
            const res = await updateRoom(req);
            if (res?.code === 1000) {
                success("Cập nhật phòng thành công");
                navigate("/admin/room-list");
            } else {
                error(res?.msg);
            }
        } catch (err) {
            error("Có lỗi xảy ra khi cập nhật phòng");
        }
    };

    useEffect(() => {
        if (id) {
            fetchRoomDetails();
        }
    }, [id]);

    if (!room) return <></>;

    return (
        <div className='MovieSchedule'>
            <header>Chỉnh sửa phòng</header>
            <Form
                name="edit-room"
                labelCol={{ span: 8 }}
                wrapperCol={{ span: 16 }}
                style={{ maxWidth: 600 }}
                onFinish={onFinish}
                initialValues={initialValues}
                colon={false}
            >
                <Form.Item
                    label="Tên phòng"
                    name="name"
                    rules={[{ required: true, message: 'Không được để trống!' }]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    label="Số hàng ghế"
                    name="verticalSeats"
                    rules={[{ required: true, message: 'Không được để trống!' }]}
                >
                    <InputNumber
                        style={{ width: '100%' }}
                        placeholder="Số hàng ghế"
                    />
                </Form.Item>

                <Form.Item
                    label="Số chỗ mỗi hàng"
                    name="horizontalSeats"
                    rules={[{ required: true, message: 'Không được để trống!' }]}
                >
                    <InputNumber
                        style={{ width: '100%' }}
                        placeholder="Số chỗ mỗi hàng"
                    />
                </Form.Item>

                <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                    <Button type="primary" htmlType="submit">
                        Cập nhật
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default EditRoom;
