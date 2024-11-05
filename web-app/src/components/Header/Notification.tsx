import React, {useState, useEffect, useContext} from 'react';
import { Dropdown, Menu } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell } from "@fortawesome/free-regular-svg-icons";
import {AuthContextProvider} from "../../contexts/AuthContext";
import {useNavigate} from "react-router-dom";
import Stomp from "stompjs";
import SockJS from 'sockjs-client';

const Notification = () => {
    const auth = useContext(AuthContextProvider);
    const user = auth?.userState;
    const navigate = useNavigate();
    const username = user?.user?.username;

    const [notifications, setNotifications] = useState<string[]>([]);
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
        label: <span>{notification}</span>,
    }));

    const notificationMenu = <Menu items={notificationItems} />;

    return (
        <Dropdown overlay={notificationMenu} trigger={['click']}>
            <FontAwesomeIcon icon={faBell} size='2x' style={{ cursor: 'pointer', marginRight: '15px' }} />
        </Dropdown>
    );
}

export default Notification;
