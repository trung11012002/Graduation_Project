import React from 'react';
import { Space, Table, Tag, Tooltip } from "antd";
import type { ColumnsType } from 'antd/es/table';
import { converDate, converTime } from '../FuctionGlobal';

interface ITableRoom {
    dataSource: any
}

const TableBooked: React.FC<ITableRoom> = ({ dataSource }) => {

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
            title: 'Email',
            key: 'email',
            dataIndex: 'email',
            align: 'center',
        },
        {
            title: 'Thời gian đặt',
            key: 'bookedTime',
            dataIndex: 'bookedTime',
            render: (_, record) => {
                return (
                    <>
                        <span>{converDate(record.bookedTime)}</span><br />
                        <span>{converTime(record.bookedTime)}</span>
                    </>
                )
            },
            align: 'center',
        },
        {
            title: 'Số lượng vé',
            key: 'numberOfTicket',
            dataIndex: 'numberOfTicket',
            align: 'center',
        },
        {
            title: 'Loại ghế',
            key: 'username',
            dataIndex: 'username',
            render: (_, record) => {
                return (
                    <Space size={"small"}>
                        <Tag
                            bordered={false}
                            color="default"
                            style={{
                                backgroundColor: '#f0f0f0',
                                color: '#444',
                                fontWeight: 'bold',
                                textTransform: 'uppercase'
                            }}
                        >
                            {`Thường: ${record.regulars}`}
                        </Tag>
                        <Tag
                            bordered={false}
                            color="red"
                            style={{
                                backgroundColor: '#c0392b',
                                color: '#fff',
                                fontWeight: 'bold',
                                textTransform: 'uppercase',
                                boxShadow: '0 4px 8px rgba(0,0,0,0.2)',
                                cursor: 'pointer'
                            }}
                        >
                            {`VIP: ${record.vips}`}
                        </Tag>
                    </Space>
                )
            },
            align: 'center',
        },
        {
            title: 'Vị trí ngồi',
            key: 'seats',
            dataIndex: 'seats',
            align: 'center',
        },
        {
            title: 'Thanh toán',
            key: 'totalPaid',
            dataIndex: 'totalPaid',
            render: (_, record) => {
                return (
                    <Tooltip title={`Thanh toán: ${record.totalPaid} VNĐ`}>
                        <span>{record.totalPaid.toLocaleString()} VNĐ</span>
                    </Tooltip>
                )
            },
            align: 'center',
        },
    ];

    return (
        <Table
            bordered
            columns={columns}
            dataSource={dataSource}
            pagination={false}
            rowKey="key"
        />
    )
}

export default TableBooked;
