import {useContext, useEffect, useState} from 'react';
import './Header.css';
import {Link, useLocation, useNavigate} from 'react-router-dom';
import {AuthContextProvider} from '../../contexts/AuthContext';
import {Button, Dropdown, Menu, message, Modal, Space} from 'antd';
import Avatar from '../Avatar/Avatar';
import {CloseOutlined, ExclamationCircleFilled} from '@ant-design/icons';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faBell} from '@fortawesome/free-regular-svg-icons';
import {WebSocketContext} from "../../contexts/WebSocketContext";
import Stomp from "stompjs";
import SockJS from 'sockjs-client';
import NotificationComponent from "./Notification";
import {geminiAi} from "../../apis/geminiAi";

const {confirm} = Modal;


const HeaderWeb = () => {
    const [urlCurrent, setUrlCurrent] = useState<string>('');
    const [notifications, setNotifications] = useState<string[]>([]); // Danh sách thông báo
    const location = useLocation();

    const auth = useContext(AuthContextProvider);
    const user = auth?.userState;
    const navigate = useNavigate();
    const username = user?.user?.username;
    // const websocket = useContext(WebSocketContext);
    // const stompClientGlobal = websocket?.stompClientGlobal;
    // const stompClientUser = websocket?.stompClientUser;

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
    console.log(user);

    const notificationItems = notifications.map((notification, index) => ({
        key: index.toString(),
        label: <span>{notification}</span>,
    }));

    const notificationMenu = <Menu items={notificationItems}/>;

    useEffect(() => {
        const checkUrl = location.pathname.split('/')[1];
        setUrlCurrent(checkUrl);

        // Ví dụ: Fetch thông báo từ server
        setNotifications(['Thông báo 1', 'Thông báo 2', 'Thông báo 3']);
    }, [location]);


    const notificationsFromParent: string[] = [];
    function receiveMessage(message: any) {
        const messageBody = JSON.parse(message.body);
        notificationsFromParent.push(messageBody.content);
        // setNotifications([...notifications, messageBody.content]);
    }

    return (
        <div className='header'>
            <div className='logo'><img src="https://res.cloudinary.com/dme0cssq0/image/upload/v1732725928/film-booking-high-resolution-logo-transparent_5_mksz3f.webp" /></div>
            <div className='header-right'>
                {user?.isLogin ? (
                    <div
                        style={{fontSize: '1.2rem', display: 'flex', alignItems: 'center'}}
                        className='auth'
                    >
                        <NotificationComponent></NotificationComponent>

                        <Link to='/' className={`${urlCurrent === '' ? 'bottomCurrent' : ''}`}>
                            <span>Trang chủ</span>
                        </Link>
                        <Link to='/showtimes' className={`${urlCurrent === 'showtimes' ? 'bottomCurrent' : ''}`}>
                            <span>Lịch chiếu</span>
                        </Link>
                        <Link to='/movie-list' className={`${urlCurrent === 'movie-list' ? 'bottomCurrent' : ''}`}>
                            <span>Phim chiếu</span>
                        </Link>

                        <p className='name_header'>Xin chào {user.user?.username}</p>

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
                            <Space style={{cursor: 'pointer'}}>
                                <Avatar width='50px'/>
                            </Space>
                        </Dropdown>
                    </div>
                ) : (
                    <div className='auth'>
                        <Link to='/' className={`${urlCurrent === '' ? 'bottomCurrent' : ''}`}>
                            <span>Trang chủ</span>
                        </Link>
                        <Link to='/showtimes' className={`${urlCurrent === 'showtimes' ? 'bottomCurrent' : ''}`}>
                            <span>Lịch chiếu</span>
                        </Link>
                        <Link to='/movie-list' className={`${urlCurrent === 'movie-list' ? 'bottomCurrent' : ''}`}>
                            <span>Phim chiếu</span>
                        </Link>
                        <button onClick={navigateLoginForm}>Đăng nhập</button>
                        <button onClick={() => navigate('/register')}>Đăng ký</button>
                    </div>
                )}
            </div>
        </div>
    );
};

export default HeaderWeb;
