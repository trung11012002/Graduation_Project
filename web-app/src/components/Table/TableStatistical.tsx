import React from 'react';
import { Table, Tag } from "antd";
import type { ColumnsType } from 'antd/es/table';
import { converDate, converTime, formatVNDCurrency } from '../FuctionGlobal';
import './TableStatistical.css'; // Import CSS tùy chỉnh

interface ITableRoom {
    dataSource: any;
}

const TableStatistical: React.FC<ITableRoom> = ({ dataSource }) => {
    const columns: ColumnsType<any> = [
        {
            title: 'STT',
            dataIndex: 'key',
            key: 'key',
            align: 'center',
            className: 'table-column-header',
            render: (text) => (
                <div className="table-index">{text}</div>
            ),
        },
        {
            title: 'Ngày chiếu',
            dataIndex: 'showDate',
            key: 'showDate',
            render: (_, record) => (
                <div className="table-date">
                    <strong>{converDate(record.showDate)}</strong>
                    <br />
                    <span>{converTime(record.showDate)}</span>
                </div>
            ),
            align: 'center',
            className: 'table-column-header',
        },
        {
            title: 'Phim chiếu',
            key: 'nameFilm',
            dataIndex: 'nameFilm',
            align: 'center',
            className: 'table-column-header',
            render: (text) => (
                <Tag color="blue" className="table-film-tag">{text}</Tag>
            ),
        },
        {
            title: 'Phòng chiếu',
            key: 'nameRoom',
            dataIndex: 'nameRoom',
            align: 'center',
            className: 'table-column-header',
        },
        {
            title: 'Số vé bán được',
            key: 'ticketsSold',
            dataIndex: 'ticketsSold',
            align: 'center',
            className: 'table-column-header',
            render: (text) => (
                <strong style={{ color: '#555' }}>{text}</strong>
            ),
        },
        {
            title: 'Doanh thu',
            key: 'revenue',
            dataIndex: 'revenue',
            render: (_, record) => (
                <span style={{ fontWeight: 'bold', color: '#27ae60' }}>
                    {formatVNDCurrency(Number(record.revenue))}
                </span>
            ),
            align: 'center',
            className: 'table-column-header',
        },
    ];

    return (
        <Table
            bordered
            columns={columns}
            dataSource={dataSource}
            pagination={false}
            rowClassName="table-row"
            style={{ marginTop: 20 }}
        />
    );
};

export default TableStatistical;