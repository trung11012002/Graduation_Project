import {useContext, useEffect, useState} from 'react';
import './Header.css';
import {Link, useLocation, useNavigate} from 'react-router-dom';
import {AuthContextProvider} from '../../contexts/AuthContext';
import {Button, Dropdown, Menu, Modal} from 'antd';
import Avatar from '../Avatar/Avatar';
import {CloseOutlined, ExclamationCircleFilled} from '@ant-design/icons';
import NotificationComponent from "./Notification";

const {confirm} = Modal;

const HeaderWeb = () => {
    const [urlCurrent, setUrlCurrent] = useState('');
    const [notifications, setNotifications] = useState<string[]>([]);
    const location = useLocation();
    const auth = useContext(AuthContextProvider);
    const user = auth?.userState;
    const navigate = useNavigate();

    const navigateLoginForm = (e: any) => {
        e.preventDefault();
        navigate('/login');
    };

    const showLogoutModal = () => {
        confirm({
            title: 'Bạn có chắc chắn đăng xuất?',
            icon: <ExclamationCircleFilled/>,
            okText: 'Đăng xuất',
            closeIcon: <CloseOutlined/>,
            cancelText: 'Ở lại',
            onOk() {
                auth?.logout();
                navigate('/login');
            },
        });
    };

    useEffect(() => {
        const checkUrl = location.pathname.split('/')[1];
        setUrlCurrent(checkUrl);

        // Ví dụ: Fetch thông báo từ server
        setNotifications(['Thông báo 1', 'Thông báo 2', 'Thông báo 3']);
    }, [location]);

    return (
        <header className='header'>
            <div className='header-container'>
                <div className='logo'>
                    <img src="https://res.cloudinary.com/dme0cssq0/image/upload/v1732725928/film-booking-high-resolution-logo-transparent_5_mksz3f.webp" alt="Logo"/>
                </div>

                <nav className='nav-links'>
                    <Link to='/' className={urlCurrent === '' ? 'active' : ''}>
                        Trang chủ
                    </Link>
                    <Link to='/movie-list' className={urlCurrent === 'movie-list' ? 'active' : ''}>
                        Phim chiếu
                    </Link>
                    <Link to='/showtimes' className={urlCurrent === 'showtimes' ? 'active' : ''}>
                        Lịch chiếu phim
                    </Link>
                </nav>

                <div className='auth-section'>
                    {user?.isLogin ? (
                        <>
                            <NotificationComponent />
                            <span className='welcome-text'>Xin chào {user.user?.username}</span>
                            <Dropdown
                                menu={{
                                    items: [
                                        {key: '1', label: <Link to='/update-information'>Thông tin cá nhân</Link>},
                                        {key: '2', label: <Link to='/booking-history'>Lịch sử đặt vé</Link>},
                                        {key: '3', label: <Button onClick={showLogoutModal}>Đăng xuất</Button>}
                                    ]
                                }}
                                trigger={['click']}
                            >
                                <div className='avatar-container'>
                                    <Avatar width='40px'/>
                                </div>
                            </Dropdown>
                        </>
                    ) : (
                        <div className='auth-buttons'>
                            <button className='login-btn' onClick={navigateLoginForm}>Đăng nhập</button>
                            <button className='register-btn' onClick={() => navigate('/register')}>Đăng ký</button>
                        </div>
                    )}
                </div>
            </div>
        </header>
    );
};

export default HeaderWeb;