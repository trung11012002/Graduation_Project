import React, { useContext } from 'react';
import { Button, Image, Modal } from 'antd';
import { converDate, converTime } from '../../components/FuctionGlobal';
import { CloseOutlined, ExclamationCircleFilled } from '@ant-design/icons';
import { deleteSchedule } from '../../apis/theater';
import { MessageContextProvider } from '../../contexts/MessageContext';
import { useNavigate } from 'react-router-dom';
const { confirm } = Modal;

interface IDetail {
    data: any
    getSchedule: any
}

const Detail: React.FC<IDetail> = ({ data, getSchedule }) => {

    const mess = useContext(MessageContextProvider)
    const success = mess?.success
    const error = mess?.error

    const navigate = useNavigate()

    const showLogoutModal = (id: number) => {
        confirm({
            title: "Bạn có chắc chắn muốn xóa???",
            icon: <ExclamationCircleFilled />,
            okText: "Xóa",
            okType: 'danger',
            closeIcon: <CloseOutlined />,
            cancelText: "Hủy",
            async onOk() {
                try {
                    const res = await deleteSchedule({ scheduleId: id });
                    if (res?.code === 200) {
                        getSchedule()
                        success("Xoá thành công")
                    }
                    else {
                        error(res?.msg)
                    }
                } catch (error) {
                    console.log('', error);
                }
            },
            onCancel() { },
        });
    };

    if (!data) return <></>

    const urlImg = data.thumnails[0]
    return (
        <div className='Detail'>
            <div className='left_Detail'>
                <Image
                    width={250}
                    src={urlImg}
                />
                <div>
                    <span style={{fontWeight: "bold", fontSize: "1.1rem"}}>Tên phim: {data.name}</span>
                    <span>Ngày chiếu: {converDate(data.startTime)}</span>
                    <span>Giờ bắt đầu: {converTime(data.startTime)}</span>
                    <span>Dự kiến kết thúc: {converTime(data.endTime)}</span>
                    <span>Phòng chiếu: {data.roomName}</span>
                </div>
            </div>

            <div className='right_Detail'>
                <div>
                    <Button
                        onClick={() => { navigate(`/admin/movie-schedule/update/${data.id}`, {
                            state: data
                        }) }}
                    >
                        Sửa
                    </Button>
                </div>
                <div><Button onClick={() => showLogoutModal(data.id)} danger>Xóa</Button></div>
                <div>Còn lại: {`${data.availables}/${data.totalSeats}`}</div>
                <div><Button
                 onClick={() => {
                    navigate(`/admin/movie-schedule/view-all-booking/${data.id}`)
                }}>Xem vé đã đặt</Button></div>
            </div>
        </div>
    )
}

export default Detail