import React, { useState } from 'react';
import Modal from 'react-modal';
import './styles.css';
import {geminiAi} from "../../apis/geminiAi";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faMessage } from '@fortawesome/free-regular-svg-icons';

Modal.setAppElement('#root'); // Đảm bảo accessibility cho ứng dụng

const ChatModal = () => {
    const [isOpen, setIsOpen] = useState(false);
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState<string[]>([]); // Lưu các tin nhắn

    const openModal = () => setIsOpen(true);
    const closeModal = () => setIsOpen(false);

    const handleSendMessage = () => {
        if (message.trim() !== '') {
            const messageUser = `Tôi: ${message}`;
            setMessages((prevMessages) => {
                // Cập nhật messages mới từ state cũ
                return [...prevMessages, messageUser];
            });
            setMessage(''); // Reset input message
        }
        const messageAi = geminiAi(message);
        messageAi.then((res) => {
            const messageAi = `AI: ${res}`;
            setMessages((prevMessages) => {
                // Thêm tin nhắn AI vào messages cũ
                return [...prevMessages, messageAi];
            });
        });
    };

    return (
        <div>
            <button className="btnModelChat" onClick={openModal} style={{ position: 'fixed', bottom: '20px', right: '20px' }}>
                <FontAwesomeIcon className="iconModelChat" icon={faMessage} />
            </button>

            <Modal
                isOpen={isOpen}
                onRequestClose={closeModal}
                contentLabel="Chat Modal"
                className="ModalContent"
                overlayClassName="ModalOverlay"
            >
                <div className="ModalHeader">
                    Chat with us!
                </div>
                <div className="MessageBox">
                    {messages.map((msg, index) => (
                        <div key={index} className="message">
                            <p>{msg}</p>
                        </div>
                    ))}
                </div>
                <div className="InputBox">
          <textarea
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              placeholder="Type your message..."
          />
                    <button onClick={handleSendMessage}>Send</button>
                </div>
            </Modal>
        </div>
    );
};

export default ChatModal;
