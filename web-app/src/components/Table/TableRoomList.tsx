import React from 'react';
import {Button, Table} from "antd";
import type { ColumnsType } from 'antd/es/table';

interface ITableRoomList {
    dataSource: any,
    onEdit: (recordId:any) => void
}

// STT, Tên phòng, Số hàng ghế, Số ghế mỗi hàng
const TableRoomList:React.FC<ITableRoomList> = ({dataSource, onEdit}) => {

    const columns: ColumnsType<any> = [
        {
            title: 'STT',
            dataIndex: 'key',
            key: 'key',
            align: 'center',
            className: ''
        },
        {
            title: 'Tên phòng',
            dataIndex: 'name',
            key: 'name',
            align: 'center',
            className: ''
        },
        {
            title: 'Số hàng ghế',
            key: 'verticalSeats',
            dataIndex: 'verticalSeats',
            align: 'center',
            className: ''
        },
        {
            title: 'Số ghế mỗi hàng',
            key: 'horizontalSeats',
            dataIndex: 'horizontalSeats',
            align: 'center',
            className: ''
        },
        {
            title: 'Hành động',
            key: 'action',
            align: 'center',
            render: (_, record) => (
                <Button onClick={() => onEdit(record.id)}>
                    Sửa
                </Button>
            ),
        },
    ];

  return (
    <Table
            bordered={true}
            columns={columns} dataSource={dataSource}
            pagination={false}
        />
  )
}

export default TableRoomList