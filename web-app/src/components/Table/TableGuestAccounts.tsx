import React, { useContext } from 'react';
import { Button, Table, Space, Tag, Tooltip } from "antd";
import { ColumnsType } from 'antd/es/table';
import { MessageContextProvider } from '../../contexts/MessageContext';
import { changeStatus } from '../../apis/user';
import { CheckCircleOutlined, BlockOutlined } from '@ant-design/icons';

interface ITableRoom {
    dataSource: any
    getAccount: any
}

const TableGuestAccounts: React.FC<ITableRoom> = ({ dataSource, getAccount }) => {
    const mess = useContext(MessageContextProvider);
    const success = mess?.success;
    const error = mess?.error;

    const change = async (id: number) => {
        const res = await changeStatus({ id: id });
        if (res?.code === 1000) {
            getAccount();
            success("Cập nhật trạng thái thành công");
        } else {
            error(res?.msg);
        }
    };

    const columns: ColumnsType<any> = [
        {
            title: 'STT',
            dataIndex: 'key',
            key: 'key',
            align: 'center',
        },
        {
            title: 'Họ Tên',
            dataIndex: 'fullname',
            key: 'fullname',
            align: 'center',
        },
        {
            title: 'Username',
            dataIndex: 'username',
            key: 'username',
            align: 'center',
        },
        {
            title: 'Email',
            dataIndex: 'email',
            key: 'email',
            align: 'center',
        },
        {
            title: 'Địa Chỉ',
            dataIndex: 'address',
            key: 'address',
            align: 'center',
        },
        {
            title: 'Trạng Thái',
            key: 'blocked',
            dataIndex: 'blocked',
            render: (_, record) => {
                return (
                    <>
                        {!record.blocked ? (
                            <Space size="middle">
                                <Tag color="green">Hoạt động</Tag>
                                <Tooltip title="Chặn tài khoản">
                                    <Button
                                        type="primary"
                                        icon={<BlockOutlined />}
                                        size="small"
                                        onClick={() => change(Number(record.id))}
                                    >
                                        Chặn
                                    </Button>
                                </Tooltip>
                            </Space>
                        ) : (
                            <Space size="middle">
                                <Tag color="red">Đã chặn</Tag>
                                <Tooltip title="Bỏ chặn tài khoản">
                                    <Button
                                        type="default"
                                        icon={<CheckCircleOutlined />}
                                        size="small"
                                        onClick={() => change(Number(record.id))}
                                    >
                                        Bỏ chặn
                                    </Button>
                                </Tooltip>
                            </Space>
                        )}
                    </>
                );
            },
            align: 'center',
        }
    ];

    return (
        <div style={{ margin: '20px', backgroundColor: '#fff', borderRadius: '8px', padding: '20px' }}>
            <Table
                bordered
                columns={columns}
                dataSource={dataSource}
                pagination={false}
                rowClassName="hover-row"
                style={{ boxShadow: '0px 4px 10px rgba(0, 0, 0, 0.1)' }}
            />
        </div>
    );
};

export default TableGuestAccounts;
