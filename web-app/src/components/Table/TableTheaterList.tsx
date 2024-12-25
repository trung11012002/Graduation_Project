import React, { useContext, useState } from "react";
import { Button, Table, Space, Tooltip } from "antd";
import { ColumnsType } from "antd/es/table";
import { MessageContextProvider } from "../../contexts/MessageContext";
import { changeStatusCinema, editCinema } from "../../apis/cinema";
import EditTheaterModal from "../../admin/TheaterList/EditTheaterModal";
import { CheckCircleOutlined, StopOutlined } from "@ant-design/icons";

interface ITableRoom {
    dataSource: any;
    getTheaterAll: () => void;
}

const TableTheaterList: React.FC<ITableRoom> = ({ dataSource, getTheaterAll }) => {
    const mess = useContext(MessageContextProvider);
    const success = mess?.success;
    const error = mess?.error;

    // State cho Edit Modal
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [editingRecord, setEditingRecord] = useState<any>(null);

    const handleEdit = (record: any) => {
        setIsModalVisible(true);
        setEditingRecord(record);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
        setEditingRecord(null);
    };

    const handleSubmitEdit = async (values: any) => {
        const updatedData = { id: editingRecord.id, ...values };
        const res = await editCinema(updatedData);
        if (res?.code === 1000) {
            success("Cập nhật rạp thành công!");
            setIsModalVisible(false);
        } else {
            error(res?.msg || "Cập nhật thất bại!");
        }
        getTheaterAll();
    };

    const change = async (id: number) => {
        const res = await changeStatusCinema({ id: id });
        if (res?.code === 1000) {
            success("Cập nhật trạng thái thành công!");
        } else {
            error(res?.msg);
        }
        getTheaterAll();
    };

    const columns: ColumnsType<any> = [
        {
            title: "STT",
            dataIndex: "key",
            key: "key",
            align: "center",
        },
        {
            title: "Tên Rạp",
            dataIndex: "name",
            key: "name",
            align: "center",
        },
        {
            title: "Địa Chỉ",
            key: "address",
            dataIndex: "address",
            align: "center",
        },
        {
            title: "Admin",
            key: "fullname",
            dataIndex: "fullname",
            align: "center",
        },
        {
            title: "Trạng thái",
            key: "action",
            render: (_, record) => (
                <Space size="middle" style={{ justifyContent: "center" }}>
                    <div style={{ display: "flex", gap: "8px" }}>
                        {record.status ? (
                            <Tooltip title="Đặt trạng thái là Inactive">
                                <Button
                                    size="small"
                                    onClick={() => change(Number(record.id))}
                                    icon={<CheckCircleOutlined />}
                                    style={{
                                        backgroundColor: "green",
                                        color: "white",
                                    }}
                                >
                                    Active
                                </Button>
                            </Tooltip>
                        ) : (
                            <Tooltip title="Đặt trạng thái là Active">
                                <Button
                                    size="small"
                                    onClick={() => change(Number(record.id))}
                                    icon={<StopOutlined />}
                                    style={{
                                        backgroundColor: "red",
                                        color: "white",
                                    }}
                                >
                                    InActive
                                </Button>
                            </Tooltip>
                        )}
                        <Tooltip title="Chỉnh sửa thông tin rạp">
                            <Button
                                size="small"
                                type="primary"
                                onClick={() => handleEdit(record)}
                            >
                                Edit
                            </Button>
                        </Tooltip>
                    </div>
                </Space>
            ),
            align: "center",
        },
    ];

    return (
        <>
            <Table bordered columns={columns} dataSource={dataSource} pagination={false} />

            {/* Modal chỉnh sửa thông tin rạp */}
            <EditTheaterModal
                visible={isModalVisible}
                onCancel={handleCancel}
                onSubmit={handleSubmitEdit}
                initialData={editingRecord}
                getTheaterAll={getTheaterAll}
            />
        </>
    );
};

export default TableTheaterList;
