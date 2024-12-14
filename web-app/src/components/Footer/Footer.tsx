import React from "react";
import "./Footer.css";

const Footer: React.FC = () => {
    return (
        <footer className="footer">
            <div className="footer-container">
                {/* Logo và Slogan */}
                <div className="footer-section logo-section">
                    <img
                        src="https://res.cloudinary.com/dbym9b0xi/image/upload/v1732724157/film-booking-high-resolution-logo-transparent_2_jl4w9a.webp"
                        alt="Cinema Logo"
                        className="footer-logo"
                    />
                    <p className="slogan">
                        Trải nghiệm điện ảnh tuyệt vời tại rạp chiếu phim của chúng tôi!
                    </p>
                </div>

                {/* Liên kết nhanh */}
                <div className="footer-section links-section">
                    <h3>Liên kết nhanh</h3>
                    <ul>
                        <li>
                            <a href="/about">Về chúng tôi</a>
                        </li>
                        <li>
                            <a href="/movies">Lịch chiếu</a>
                        </li>
                        <li>
                            <a href="/contact">Liên hệ</a>
                        </li>
                        <li>
                            <a href="/faq">Câu hỏi thường gặp</a>
                        </li>
                    </ul>
                </div>

                {/* Thông tin liên hệ */}
                <div className="footer-section contact-section">
                    <h3>Liên hệ</h3>
                    <p>Địa chỉ: 123 Đường Thanh Bình, Quận Hà Đông, Hà Nội</p>
                    <p>Điện thoại: (098) 982 7309 </p>
                    <p>Email: support_doan@cinema.com</p>
                </div>

                {/* Kết nối mạng xã hội */}
                <div className="footer-section social-section">
                    <h3>Kết nối với chúng tôi</h3>
                    <div className="social-icons">
                        <a href="https://facebook.com" target="_blank" rel="noopener noreferrer">
                            Facebook
                        </a>
                        <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">
                            Instagram
                        </a>
                        <a href="https://youtube.com" target="_blank" rel="noopener noreferrer">
                            YouTube
                        </a>
                    </div>
                </div>
            </div>

            <div className="footer-bottom">
                <p>© 2024 Cinema Website. All rights reserved.</p>
            </div>
        </footer>
    );
};

export default Footer;
