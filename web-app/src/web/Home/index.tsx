import React, { useContext, useState, useEffect } from 'react';
import './index.css';
import HeaderWeb from '../../components/Header/HeaderWeb';
import { getMoviesByName } from '../../apis/movie';
import Detail from '../../manager/MovieList/Detail';
import { MessageContextProvider } from '../../contexts/MessageContext';
import PaginationCustom from '../../components/Pagination/PaginationCustom';
import './SpotlightEffect.css';
import { Button, Input, Select, Space } from 'antd';

const Web = () => {
  const mess = useContext(MessageContextProvider);
  const success = mess?.success;
  const error = mess?.error;

  const [name, setName] = useState<string>('');
  const [movies, setMovies] = useState<any>();
  const [pageCurrent, setPageCurrent] = useState<number>(1);
  const [pageSize] = useState<number>(5);
  const [totalItems, setTotalItems] = useState<number>(0);
  const [isSearching, setIsSearching] = useState<boolean>(false);

  const getMovies = async (page: number = 1, pageSize: number = 5) => {
    if (!name) {
      error("Vui lòng nhập phim muốn tìm kiếm");
      setMovies(null);
      return;
    }

    const data = {
      name: name,
      page: page,
      perPage: pageSize,
    };

    const res = await getMoviesByName(data);
    if (res?.code === 1000) {
      if (res.data.films.length) {
        setMovies(res.data.films);
        setTotalItems(res.data.pageInfo.totalItems);
        setPageCurrent(page); // Cập nhật trang hiện tại
        // success("Phim đã được tìm thấy");
      } else {
        error("Không có phim trong danh sách");
        setMovies(null);
      }
    } else {
      error("Không có phim trong danh sách");
      setMovies(null);
    }
    setIsSearching(false); // Đặt lại isSearching về false sau khi tải xong dữ liệu
  };

  useEffect(() => {
    if (isSearching ) { // Gọi getMovies khi đang tìm kiếm hoặc khi trang thay đổi
      getMovies(pageCurrent);
    }
  }, [pageCurrent, isSearching]);

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!name) {
      error("Vui lòng nhập phim muốn tìm kiếm");
      return;
    }
    setIsSearching(true); // Đặt isSearching thành true để bắt đầu tìm kiếm
    setPageCurrent(1); // Reset về trang đầu khi có tìm kiếm mới
  };

  return (
      <div className='web'>
        <HeaderWeb />
        <div className='Home'>
            <form onSubmit={handleSubmit} className="search-btn-home">
              <Space.Compact style={{ width: '100%' , height : '50px' }}>
                <Input
                    onChange={(e: any) => {
                      setName(e.target.value);
                      if (!e.target.value) {
                        setMovies(null);
                      }
                    }}
                    style = {{ fontSize : 20 }} placeholder="Tìm kiếm phim muốn xem ..." />
                <Button className= "search-txt-home" htmlType="submit" style={{ height : '50px'}} type="primary">Tìm kiếm</Button>
              </Space.Compact>
            </form>


          {movies ? (
              <>
                {movies.map((movie: any, index: number) => (
                    <Detail key={index} data={movie}/>
                ))}
                <PaginationCustom
                    pageSize={pageSize}
                    total={totalItems}
                    pageCurrent={pageCurrent}
                    getFilms={(page: number) => {
                      getMovies(page, 5); // Chuyển trang
                    }}
                />
              </>
          ) : (

              <div className="spotlight-container img-home">
                <img
                    // src="https://cdnphoto.dantri.com.vn/9fJvg-3fkeZdXc7efkMQPtmeaDI=/zoom/1200_630/2021/03/08/dan-triv-2-docx-1615178918200.jpeg"
                    src="https://files.betacorp.vn/media/images/2024/11/20/1702x621-24-162735-201124-55.jpg"
                    alt="Movie Poster"
                    className="spotlight-image"
                />
              </div>
          )}
        </div>
      </div>
  );
};

export default Web;
