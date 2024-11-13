import React, {useState, useEffect, useContext} from 'react';
import { Dropdown, Menu } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell } from "@fortawesome/free-regular-svg-icons";
import {AuthContextProvider} from "../../contexts/AuthContext";
import {useNavigate} from "react-router-dom";
import Stomp from "stompjs";
import SockJS from 'sockjs-client';
import {initNotification} from "../../apis/notify";
import {Simulate} from "react-dom/test-utils";
import error = Simulate.error;
import {MessageContextProvider} from "../../contexts/MessageContext";
import { Notification } from "../../types/Notification";


const NotificationComponent = () => {
    const auth = useContext(AuthContextProvider);
    const user = auth?.userState;
    const navigate = useNavigate();
    const username = user?.user?.username;

    const mess = useContext(MessageContextProvider)
    const success = mess?.success
    const error = mess?.error

    const [notifications, setNotifications] = useState<Notification[]>([]);

    useEffect(() => {
        const fetchNotifications = async () => {
            try {
                const response = await initNotification({ username: username || "" });
                if (response && response.code === 1000) {
                    const res = JSON.parse(JSON.stringify(response.data));
                    console.log("--------------------" ,res)
                    setNotifications(res || []);
                } else {
                    error(response?.msg);
                }
            } catch (err) {
                console.error("Error fetching notifications:", err);
            }
        };

        fetchNotifications();
    }, []);

    let socket = new SockJS('http://localhost:8088/notification-service/ws');

    //lib-v1
    let stompClientGlobal = Stomp.over(socket);
    let stompClientUser = Stomp.over(socket);
    let websocketConnected = localStorage.getItem('websocketConnected');
    if(username && websocketConnected == "false"){
        let socket1 = new SockJS('http://localhost:8088/notification-service/ws');
        stompClientGlobal = Stomp.over(socket1);
        stompClientGlobal.connect({}, (frame: any) => {
            stompClientGlobal.subscribe('/notification-global', receiveMessage);
            // Send a message to the topic
            // stompClientGlobal.send('/app/notification-global', {}, JSON.stringify({
            //     content: 'Hello World',
            //     sender: 'me'
            // }));
        });
        let socket2 = new SockJS('http://localhost:8088/notification-service/ws');
        stompClientUser = Stomp.over(socket2);
        stompClientUser.connect({username: user?.user?.username}, (frame: any) => {
            // Subscribe to a topic
            stompClientUser.subscribe('/users/notification-user/messages', receiveMessage);

        });
        localStorage.setItem('websocketConnected', "true");
    }
    function receiveMessage(message: any) {
        const messageBody = JSON.parse(message.body);
        setNotifications((prevNotifications) => [messageBody.content,...prevNotifications]);
    }

    const notificationItems = notifications.map((notification, index) => ({
        key: index.toString(),
        label: <span>{ notification.message }</span>,
    }));




    const notificationMenu = <Menu items={notificationItems} />;

    return (
        <Dropdown overlay={notificationMenu} trigger={['click']}>
            <FontAwesomeIcon icon={faBell} size='2x' style={{ cursor: 'pointer', marginRight: '15px' }} />
        </Dropdown>
    );
}

export default NotificationComponent ;
