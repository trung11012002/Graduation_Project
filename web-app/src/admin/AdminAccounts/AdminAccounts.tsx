import React, { useEffect, useState } from 'react';
import TableAdminAccounts from '../../components/Table/TableAdminAccounts';
import { findAllAdminAccount } from '../../apis/user';

const AdminAccounts = () => {

  const [listAccount, setListAccount] = useState<any>()

  const getAccount = async () => {
    const res = await findAllAdminAccount()
    if (res?.code === 1000) {
      const newData = res.data.map((value: any, index: number) => {
        return ({
          id: value.id,
          key: index,
          name: value.fullname,
          username: value.username,
          email: value.email,
          address: value.address,
          blocked: value.blocked,
          nameCinema: value.managedCinema ? value.managedCinema.name : 'Chưa có rạp',
        })
      })
      setListAccount(newData)
    }
  }

  useEffect(() => {
    getAccount()
  }, [])

  return (
    <div className='MovieSchedule'>
      <header>Danh sách tài khoản admin</header>
      <TableAdminAccounts dataSource={listAccount} getAccount={getAccount} />
    </div>
  )
}

export default AdminAccounts