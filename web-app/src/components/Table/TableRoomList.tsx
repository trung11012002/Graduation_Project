import React from 'react';
import { Button, Table, Tooltip } from "antd";
import type { ColumnsType } from 'antd/es/table';

interface ITableRoomList {
    dataSource: any,
    onEdit: (recordId: any) => void
}

// STT, Tên phòng, Số hàng ghế, Số ghế mỗi hàng
const TableRoomList: React.FC<ITableRoomList> = ({ dataSource, onEdit }) => {

    const columns: ColumnsType<any> = [
        {
            title: 'STT',
            dataIndex: 'key',
            key: 'key',
            align: 'center',
        },
        {
            title: 'Tên phòng',
            dataIndex: 'name',
            key: 'name',
            align: 'center',
        },
        {
            title: 'Số hàng ghế',
            dataIndex: 'verticalSeats',
            key: 'verticalSeats',
            align: 'center',
        },
        {
            title: 'Số ghế mỗi hàng',
            dataIndex: 'horizontalSeats',
            key: 'horizontalSeats',
            align: 'center',
        },
        {
            title: 'Hành động',
            key: 'action',
            align: 'center',
            render: (_, record) => (
                <Tooltip title="Chỉnh sửa phòng">
                    <Button
                        size="small"
                        onClick={() => onEdit(record.id)}
                        style={{ backgroundColor: '#4CAF50', color: 'white', border: 'none' }}
                        onMouseEnter={(e) => e.currentTarget.style.backgroundColor = '#45a049'}
                        onMouseLeave={(e) => e.currentTarget.style.backgroundColor = '#4CAF50'}
                    >
                        Sửa
                    </Button>
                </Tooltip>
            ),
        },
    ];

    return (
        <Table
            bordered
            columns={columns}
            dataSource={dataSource}
            pagination={false}
            rowKey="key"  // Đảm bảo mỗi hàng có một key duy nhất
        />
    );
};

export default TableRoomList;
