import { useEffect } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

const MyComponent = () => {
//     useEffect(() => {
//         const socket = new SockJS('http://localhost:8088/notification-service/ws');
//         const stompClient = Stomp.over(socket);
//
//         stompClient.connect({ username: "test" }, (frame: any) => {
//             console.log('Connected: ' + frame);
//
//             // Subscribe to global notification topic
//             stompClient.subscribe('/notification-global', (message) => {
//                 console.log('Received global message:', message.body);
//             });
//
//             // Subscribe to user-specific messages
//             stompClient.subscribe('/users/notification-user/messages', (message) => {
//                 console.log('Received user-specific message:', message.body);
//             });
//
//             // Gửi tin nhắn đến topic global
//             stompClient.send('/app/notification-global', {}, JSON.stringify({ content: 'Hello World', sender: 'me' }));
//         });
//
//         // Cleanup when the component is unmounted
//         return () => {
//             stompClient.disconnect(() => {
//                 console.log('Disconnected');
//             });
//         };
//     }, []);
//
    return (
        <div>
            {/* Your component content */}
        </div>
    );
};
//
export default MyComponent;
