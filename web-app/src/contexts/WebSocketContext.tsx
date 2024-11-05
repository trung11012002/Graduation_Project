import React, {createContext, ReactNode, useContext, useEffect, useRef} from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
// import Stomp, { Client } from 'stompjs';
import { AuthContextProvider } from "./AuthContext";

// interface WebSocketContextProps {
//     stompClient: React.MutableRefObject<Client | null>;
// }
interface IWebsocketContext {
    // stompClientGlobal: any;
    // stompClientUser: any;
    test: string;
}

const WebSocketContext = createContext<IWebsocketContext| undefined>(undefined);

interface IWebSocketProvider {
    children: ReactNode;
}


const WebSocketProvider: React.FC<IWebSocketProvider> = ({ children }) => {
    const auth = useContext(AuthContextProvider);
    const user = auth?.userState;
    const username = user?.user?.username;

    // // const stompClient = useRef(null);
    // let socket = new SockJS('http://localhost:8088/notification-service/ws');
    //
    // //lib-v1
    // let stompClientGlobal = Stomp.over(socket);
    // let stompClientUser = Stomp.over(socket);
    // let websocketConnected = localStorage.getItem('websocketConnected');
    // if(username && websocketConnected == "false"){
    //     console.log("user1111", user?.user?.username);
    //
    //     // let socket1 = new SockJS('http://localhost:8088/notification-service/ws');
    //     // stompClientGlobal = Stomp.over(socket1);
    //     // stompClientGlobal.connect({}, (frame: any) => {
    //     //     // Subscribe to a topic
    //     //     // stompClientGlobal.subscribe('/notification-global', (message) => {
    //     //     //     console.log('Received message:', message.body);
    //     //     // });
    //     //     // Send a message to the topic
    //     //     // stompClientGlobal.send('/app/notification-global', {}, JSON.stringify({
    //     //     //     content: 'Hello World',
    //     //     //     sender: 'me'
    //     //     // }));
    //     // });
    //     // let socket2 = new SockJS('http://localhost:8088/notification-service/ws');
    //     // stompClientUser = Stomp.over(socket2);
    //     // stompClientUser.connect({username: user?.user?.username}, (frame: any) => {
    //     //     // Subscribe to a topic
    //     //     // stompClientUser.subscribe('/users/notification-user/messages', (message) => {
    //     //     //     console.log('Received message:', message.body);
    //     //     // });
    //     //
    //     // });
    //     localStorage.setItem('websocketConnected', "true");
    // }
    // useEffect(() => {
    //     if(username){
    //
    //
    //         // Hủy kết nối khi component bị unmount
    //         return () => {
    //             stompClientGlobal.disconnect(() => {
    //                 console.log('WebSocket disconnected global');
    //             });
    //             stompClientUser.disconnect(() => {
    //                 console.log('WebSocket disconnected user');
    //             });
    //         };
    //     }
    // }, []);

    const value = {
        // stompClientGlobal,
        // stompClientUser
        test: "test"
    };

    return (
        <WebSocketContext.Provider value={value}>
            {children}
        </WebSocketContext.Provider>
    );
}

export {WebSocketContext, WebSocketProvider};