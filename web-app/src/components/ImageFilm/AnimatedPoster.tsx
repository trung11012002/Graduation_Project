import React, { useState } from 'react';
import { motion } from 'framer-motion';
import './TogglePlayBtn.css';
interface ModalComponentProps {
    videoUrl: string; // URL video YouTube
    onClose: () => void; // Hàm đóng modal
}

const ModalComponent: React.FC<ModalComponentProps> = ({ videoUrl, onClose }) => {
    return (
        <div
            style={{
                position: 'fixed',
                top: 0,
                left: 0,
                width: '100%',
                height: '100%',
                backgroundColor: 'rgba(0, 0, 0, 0.8)',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                zIndex: 1000,
                padding: '20px',
            }}
            onClick={onClose} // Đóng modal khi nhấn ngoài
        >
            <div
                style={{
                    position: 'relative',
                    width: '60%', // Giảm chiều rộng của modal
                    height: '70%', // Giảm chiều cao của modal
                    backgroundColor: '#fff',
                    borderRadius: '10px',
                    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.3)',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    padding: '20px', // Thêm padding để nội dung không bị dính sát viền
                }}
            >
                <iframe
                    width="100%"
                    height="100%"
                    src={videoUrl}
                    title="YouTube video player"
                    frameBorder="0"
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                    allowFullScreen
                    style={{
                        borderRadius: '10px',
                    }}
                />
            </div>
        </div>
    );
};

interface AnimatedPosterProps {
    imgUrl: string; // URL ảnh
    videoUrl: string; // URL video YouTube
}

const AnimatedPoster: React.FC<AnimatedPosterProps> = ({ imgUrl, videoUrl }) => {
    const [isModalOpen, setModalOpen] = useState<boolean>(false);

    return (
        <>
            <motion.div
                className={'poster-img'}
                // whileHover={{
                //     scale: 1.1,
                //     boxShadow: '0 10px 20px rgba(0, 0, 0, 0.3)',
                // }}
                transition={{ type: 'spring', stiffness: 300 }}
                style={{
                    position: 'relative',
                    width: '300px',
                    height: '450px',
                    overflow: 'hidden',
                    borderRadius: '15px',
                    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
                    cursor: 'pointer',
                }}
            >
                <img
                    className={'poster-img-v2'}
                    src={imgUrl} // Sử dụng props imgUrl
                    alt="Movie Poster"
                    style={{
                        width: '100%',
                        height: '100%',
                        objectFit: 'cover',
                    }}
                />
                {/* Nút Play hiển thị khi hover vào poster */}
                <motion.div
                    className={'play-btn'}
                    // whileHover={{ opacity: 1 }} // Nút Play sẽ hiển thị khi hover
                    // initial={{ opacity: 0 }} // Ban đầu ẩn nút Play
                    transition={{ duration: 0.3 }}
                    onClick={() => setModalOpen(true)}
                    style={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: '60px',
                        height: '60px',
                        backgroundColor: 'rgba(0, 0, 0, 0.6)',
                        borderRadius: '50%',
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                        cursor: 'pointer',
                        // opacity: 0,
                        transition: 'opacity 0.3s ease-in-out',
                    }}
                >
                <span
                    style={{
                        width: 0,
                        height: 0,
                        borderLeft: '20px solid white',
                        borderTop: '10px solid transparent',
                        borderBottom: '10px solid transparent',
                    }}
                />
                </motion.div>
            </motion.div>
            {isModalOpen && (
                <ModalComponent
                    videoUrl={videoUrl}
                    onClose={() => setModalOpen(false)}
                />
            )}
        </>
    );
};

export default AnimatedPoster;
