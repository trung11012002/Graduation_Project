import React, { useEffect, useState } from "react";
import { Modal, Form, Input, Button, Select } from "antd";
import { findAllAdminAccountWithoutCinema } from "../../apis/user";

// Định nghĩa kiểu dữ liệu cho prop và Admin
interface EditTheaterModalProps {
    visible: boolean;
    onCancel: () => void;
    onSubmit: (values: any) => void;
    initialData: any;
}

interface Admin {
    id: string;
    fullname: string;
}
interface EditTheaterModalProps {
    visible: boolean;
    onCancel: () => void;
    onSubmit: (values: any) => void;
    initialData: any;
    getTheaterAll: () => void;
}
const EditTheaterModal: React.FC<EditTheaterModalProps> = ({
                                                               visible,
                                                               onCancel,
                                                               onSubmit,
                                                               initialData,
                                                               getTheaterAll,
                                                           }) => {
    const [form] = Form.useForm();
    const [adminList, setAdminList] = useState<{ value: string; label: string }[]>([]);

    // Lấy dữ liệu admin từ API và set giá trị cho adminList
    useEffect(() => {
        const fetchAdminList = async () => {
            try {
                const res = await findAllAdminAccountWithoutCinema();
                if (res?.data) {
                    // Nếu có initialData, set giá trị mặc định cho form
                    const adminCurrent = {
                        value: initialData.id,
                        label: initialData.fullname,
                    };
                    if (initialData) {
                        form.setFieldsValue({
                            name: initialData.name,
                            address: initialData.address,
                            adminId: initialData.id,
                        });
                    }
                    // Chuyển đổi dữ liệu từ API thành định dạng mà Select cần
                    const newData = res.data.map((value: Admin) => ({
                        value: value.id,
                        label: value.fullname,
                    }));

                    const newDatas = [adminCurrent, ...newData];
                    setAdminList(newDatas);
                }
            } catch (error) {
                console.error("Error fetching admin list:", error);
            }
        };

        fetchAdminList();
    }, [initialData, form]);

    return (
        <Modal
            title="Chỉnh sửa thông tin rạp"
            visible={visible}
            onCancel={onCancel}
            footer={null}
        >
            <Form form={form} layout="vertical" onFinish={onSubmit}>
                <Form.Item
                    label="Tên Rạp"
                    name="name"
                    rules={[{ required: true, message: "Vui lòng nhập tên rạp!" }]}
                >
                    <Input />
                </Form.Item>
                <Form.Item
                    label="Địa Chỉ"
                    name="address"
                    rules={[{ required: true, message: "Vui lòng nhập địa chỉ!" }]}
                >
                    <Input />
                </Form.Item>
                <Form.Item
                    label="Admin"
                    name="adminId"
                    rules={[{ required: true, message: "Vui lòng chọn Admin!" }]}
                >
                    <Select
                        placeholder="Chọn Admin"
                        options={adminList} // Sử dụng trực tiếp adminList đã được chuyển đổi
                    />
                </Form.Item>

                <Form.Item>
                    <Button type="primary" htmlType="submit">
                        Lưu
                    </Button>
                    <Button style={{ marginLeft: 8 }} onClick={onCancel}>
                        Hủy
                    </Button>
                </Form.Item>
            </Form>
        </Modal>
    );
};

export default EditTheaterModal;
