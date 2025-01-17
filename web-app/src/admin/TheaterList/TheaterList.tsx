import React, { useEffect, useState } from 'react';
import TableTheaterList from '../../components/Table/TableTheaterList';
import { GetAllCinema } from '../../apis/theater';


const TheaterList = () => {

  const [listTheater, setListTheater] = useState<any>()
  useEffect(() => {
    getTheaterAll()
  }, [])

  const getTheaterAll = () => {
    (async() => {
      const res = await GetAllCinema()
      if(res?.code === 1000) {
        const newData = res.data.map((value: any, index: number) => {
          return({
            key: index,
            ...value,
            fullname: value.admin.fullname
          })
        })
        setListTheater(newData)
      }
    })()
  }

  return (
    <div className='MovieSchedule'>
      <header>Danh sách rạp</header>
        <TableTheaterList dataSource={listTheater} getTheaterAll = {getTheaterAll}/>
    </div>
  )
}

export default TheaterList
