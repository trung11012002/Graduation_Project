import {useContext, useEffect, useState} from 'react';
import './Header.css';
import {Link, useLocation, useNavigate} from 'react-router-dom';
import {AuthContextProvider} from '../../contexts/AuthContext';
import {Button, Dropdown, Menu, Modal, Space} from 'antd';
import Avatar from '../Avatar/Avatar';
import {CloseOutlined, ExclamationCircleFilled} from '@ant-design/icons';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faBell} from '@fortawesome/free-regular-svg-icons';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import {Client} from '@stomp/stompjs';

const {confirm} = Modal;


let socket = new SockJS('http://localhost:8088/notification-service/ws');
let stompClient = Stomp.over(socket);
const HeaderWeb = () => {
    const [urlCurrent, setUrlCurrent] = useState<string>('');
    const [notifications, setNotifications] = useState<string[]>([]); // Danh sách thông báo
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

    function sleep(ms: any) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }


    //lib-v1
    let stompClient1 = Stomp.over(socket);
    let stompClient2 = Stomp.over(socket);

    useEffect(() => {
        socket = new SockJS('http://localhost:8088/notification-service/ws');
        stompClient1 = Stomp.over(socket);
        stompClient1.connect({}, (frame: any) => {
            console.log('Connected: ' + frame);

            // Subscribe to a topic
            stompClient1.subscribe('/notification-global', (message) => {
                console.log('Received message:', message.body);
            });


            // Gửi tin nhắn
            stompClient1.send('/app/notification-global', {}, JSON.stringify({content: 'Hello World', sender: 'me'}));
        });
        socket = new SockJS('http://localhost:8088/notification-service/ws');
        stompClient2 = Stomp.over(socket);
        stompClient2.connect({ username: "test" }, (frame: any) => {
            console.log('Connected: ' + frame);

            // Subscribe to a topic
            stompClient2.subscribe('/users/notification-user/messages', (message) => {
                console.log('Received message:', message.body);
            });

        });
        // Cleanup khi component bị hủy
        return () => {
            stompClient.disconnect(() => {
            });
        };
    }, []);

    function onMessageReceived(payload: any) {
        console.log("okok");
        console.log(payload);

        if (payload._binaryBody) {
            // Chuyển đổi _binaryBody thành chuỗi JSON
            const bodyArray = new Uint8Array(Object.values(payload._binaryBody));
            const jsonString = String.fromCharCode(...Array.from(bodyArray));

            try {
                const jsonObject = JSON.parse(jsonString);
                console.log("Parsed JSON Body:", jsonObject);
                console.log(jsonObject.content);
            } catch (error) {
                console.error("Failed to parse JSON:", error);
            }
        } else {
            console.warn("No binary body found in payload");
        }
    }

    function sendMessage() {

        var chatMessage = {
            sender: "trung",
            content: "trung123"
        };
        stompClient1.send('/app/chat.sendMessage', {}, JSON.stringify({content: 'Hello World 1', sender: 'me'}));
        stompClient2.send('/app/hello', {}, JSON.stringify({content: 'Hello World 2', sender: 'me'}));
    }


    const handleSendMessage = () => {
        sendMessage();
    }

    return (
        <div className='header'>
            <div className='logo' onClick={handleSendMessage}>FILM BOOKING</div>
            <div className='header-right'>
                {user?.isLogin ? (
                    <div
                        style={{fontSize: '1.2rem', display: 'flex', alignItems: 'center'}}
                        className='auth'
                    >
                        <Dropdown overlay={notificationMenu} trigger={['click']}>
                            <FontAwesomeIcon icon={faBell} size='2x' style={{cursor: 'pointer', marginRight: '15px'}}/>
                        </Dropdown>

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
