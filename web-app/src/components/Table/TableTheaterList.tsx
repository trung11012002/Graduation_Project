import React, { useContext, useState } from "react";
import { Button, Table } from "antd";
import type { ColumnsType } from "antd/es/table";
import { MessageContextProvider } from "../../contexts/MessageContext";
import {changeStatusCinema, editCinema} from "../../apis/cinema";
import EditTheaterModal from "../../admin/TheaterList/EditTheaterModal";

interface ITableRoom {
    dataSource: any;
    getTheaterAll: () => void;
}

const TableTheaterList: React.FC<ITableRoom> = ({ dataSource, getTheaterAll}) => {
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
                <>
                    <div className="actionEdit"
                         style={{display: "flex", gap: "8px", justifyContent: "center"}}>
                        <div style={{marginBottom: 8}}>
                            {record.status ? (
                                <>
                                    <Button
                                        size="small"
                                        onClick={() => change(Number(record.id))}
                                        style={{backgroundColor: 'green', color: 'white'}} // Active màu xanh
                                    >
                                        Active
                                    </Button>
                                </>
                            ) : (
                                <>
                                    <Button
                                        size="small"
                                        onClick={() => change(Number(record.id))}
                                        style={{backgroundColor: 'red', color: 'white'}} // Inactive màu đỏ
                                    >
                                        InActive
                                    </Button>
                                </>
                            )}
                        </div>
                        <Button size="small" type="primary" onClick={() => handleEdit(record)}>
                            Edit
                        </Button>
                    </div>
                </>
            ),
            align: "center",
        },
    ];

    return (
        <>
            <Table bordered columns={columns} dataSource={dataSource} pagination={false}/>

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
