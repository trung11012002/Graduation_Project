import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { cinemaByDay } from '../../apis/theater';
import { converTime } from '../../components/FuctionGlobal';
import styles from './Content.module.css';

interface IContent {
  theater: any
}

const current_day: { [key: number]: string } = {
  0: "CN",
  1: "T2",
  2: "T3",
  3: "T4",
  4: "T5",
  5: "T6",
  6: "T7"
}

const Content: React.FC<IContent> = ({ theater }) => {
  const [schedules, setSchedules] = useState<any>()
  const { id } = useParams()
  const navigate = useNavigate()

  const currentDate = new Date()
  const [date, setDate] = useState<string>(
      `${currentDate.getFullYear()}-${(currentDate.getMonth() + 1).toString().padStart(2, '0')}-${currentDate.getDate().toString().padStart(2, '0')}`
  )

  currentDate.setDate(currentDate.getDate() - 1)

  const dateOption = []

  for (let i = 0; i < 7; i++) {
    currentDate.setDate(currentDate.getDate() + 1)
    const currentDayOfWeek = current_day[currentDate.getDay()];
    const day = currentDate.getDate().toString().padStart(2, '0')
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0')
    const year = currentDate.getFullYear()

    dateOption.push(
        <div
            key={i}
            className={`
          ${styles.dateItem} 
          ${date.split("-")[2] === day ? styles.dateItemActive : ''}
        `}
            onClick={() => { setDate(`${year}-${month}-${day}`) }}
        >
          <span>{day}/{month}</span>
          <span>{i === 0 ? "Hôm nay" : currentDayOfWeek}</span>
        </div>
    )
  }

  const getAllScheduleByCinema = async (cinemaId: number, date: string) => {
    const data = {
      cinemaId: cinemaId,
      date: date
    }
    const res = await cinemaByDay(data)
    if (res?.code === 200) {
      setSchedules(res.data)
    }
  }

  useEffect(() => {
    if (id) {
      getAllScheduleByCinema(Number(id), date)
    }
  }, [date, id])

  return (
      <div className={styles.container}>
        <div className={styles.header}>
          <h3>Lịch chiếu phim tại {theater?.name}</h3>
          <p>{theater?.address}</p>

          <div className={styles.dateSelector}>
            {dateOption}
          </div>
        </div>

        <div className={styles.movieList}>
          {schedules && schedules.length ? (
              schedules.map((schedule: any, index: number) => {
                const typeNames = schedule.filmResponse.typeNames.join(', ');
                const urlImage = schedule.filmResponse.thumnails[0];

                return (
                    <div key={index} className={styles.movieItem}>
                      <div className={styles.moviePoster}>
                        <img src={urlImage} alt={schedule.filmResponse.name} />
                      </div>

                      <div className={styles.movieDetails}>
                        <div className={styles.movieName}>{schedule.filmResponse.name}</div>
                        <div className={styles.movieType}>{typeNames}</div>

                        <div className={styles.showtimeList}>
                          {schedule.scheduleResponseList.map((value: any, timeIndex: number) => (
                              <div
                                  key={timeIndex}
                                  className={styles.showtimeItem}
                                  onClick={() => navigate(`/showtimes/${id}/choose-seats/${value.id}`)}
                              >
                                {converTime(value.startTime)} ~ {converTime(value.endTime)}
                              </div>
                          ))}
                        </div>
                      </div>
                    </div>
                )
              })
          ) : (
              <div className={styles.noShowtimes}>
                Không có phim nào được chiếu hôm nay 😢
              </div>
          )}
        </div>
      </div>
  )
}

export default Content