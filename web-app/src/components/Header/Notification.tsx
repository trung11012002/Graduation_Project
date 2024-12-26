import React, { useState, useEffect, useContext } from 'react';
import { Dropdown, Menu } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell } from "@fortawesome/free-regular-svg-icons";
import { AuthContextProvider } from "../../contexts/AuthContext";
import { useNavigate } from "react-router-dom";
import Stomp from "stompjs";
import SockJS from 'sockjs-client';
import { initNotification } from "../../apis/notify";
import { MessageContextProvider } from "../../contexts/MessageContext";
import { Notification } from "../../types/Notification";

const NotificationComponent = () => {
    const auth = useContext(AuthContextProvider);
    const user = auth?.userState;
    const navigate = useNavigate();
    const username = user?.user?.username;

    const mess = useContext(MessageContextProvider);
    const success = mess?.success;
    const error = mess?.error;

    const [notifications, setNotifications] = useState<Notification[]>([]);
    const [unreadNotifications, setUnreadNotifications] = useState<boolean>(false);
    const [stompClients, setStompClients] = useState<{
        global: any;
        user: any;
        payment: any;
    }>({
        global: null,
        user: null,
        payment: null
    });

    // Fetch initial notifications
    useEffect(() => {
        const fetchNotifications = async () => {
            try {
                const response = await initNotification({username: username || ""});
                if (response && response.code === 1000) {
                    const res = JSON.parse(JSON.stringify(response.data));
                    setNotifications(res || []);
                    setUnreadNotifications(res.length > 0);
                } else {
                    error(response?.msg);
                }
            } catch (err) {
                console.error("Error fetching notifications:", err);
            }
        };

        if (username) {
            fetchNotifications();
        }
    }, [username]);

    // WebSocket connection setup
    useEffect(() => {
        let isConnected = false;

        const connectWebSocket = () => {
            if (!username || isConnected) return;

            try {
                // Global notifications
                const socket1 = new SockJS('http://localhost:8088/notification-service/ws');
                const stompGlobal = Stomp.over(socket1);
                stompGlobal.connect({}, () => {
                    stompGlobal.subscribe('/notification-global', receiveMessage);
                });

                // User notifications
                const socket2 = new SockJS('http://localhost:8088/notification-service/ws');
                const stompUser = Stomp.over(socket2);
                stompUser.connect({username: username}, () => {
                    stompUser.subscribe('/users/notification-user/messages', receiveMessage);
                });
                const socket3 = new SockJS('http://localhost:8088/notification-service/ws');
                const stompPayment = Stomp.over(socket3);
                stompPayment.connect({username: username}, () => {
                    stompPayment.subscribe('/users/notification-user/payment', receiveUrlPayment);
                });

                setStompClients({
                    global: stompGlobal,
                    user: stompUser,
                    payment: stompPayment
                });

                isConnected = true;
            } catch (error) {
                console.error("WebSocket connection error:", error);
            }
        };

        connectWebSocket();

        // Cleanup function
        return () => {
            if (stompClients.global) stompClients.global.disconnect();
            if (stompClients.user) stompClients.user.disconnect();
            if (stompClients.payment) stompClients.payment.disconnect();
        };
    }, [username]);

    function receiveMessage(message: any) {
        const messageBody = JSON.parse(message.body);

        if (messageBody?.content) {
            const noti: Notification = {
                id: messageBody?.id || 0,
                type: messageBody?.type || "info",
                user: messageBody?.user || username,
                message: messageBody.content,
            };

            setNotifications(prevNotifications => [noti, ...prevNotifications]);
            setUnreadNotifications(true);
        }
    }

    function receiveUrlPayment(message: any) {
        const paymentUrl = message.body;
        if (paymentUrl) {
            localStorage.setItem("paymentUrl", paymentUrl);
        }
    }

    const handleNotificationClick = () => {
        setUnreadNotifications(false);
    };

    const notificationItems = notifications.map((notification, index) => ({
        key: index.toString(),
        label: <span>{notification.message}</span>,
    }));

    const notificationMenu = <Menu items={notificationItems}/>;

    return (
        <Dropdown
            overlay={notificationMenu}
            trigger={['click']}
            placement="bottomRight"
        >
            <div className="notification-icon">
                <FontAwesomeIcon icon={faBell} />
                {/* Nếu có thông báo mới */}
                <span className="notification-badge"></span>
            </div>
        </Dropdown>
    );
};
export default NotificationComponent;
