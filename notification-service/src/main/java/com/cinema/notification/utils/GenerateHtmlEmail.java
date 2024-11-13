package com.cinema.notification.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GenerateHtmlEmail {
    public static String generateHtmlEmailWelcome(String userName) {
        return String.format("""
                    <!DOCTYPE html>
                    <html lang='en'>
                    <head>
                        <meta charset='UTF-8'>
                        <meta name='viewport' content='width=device-width, initial-scale=1.0'>
                        <title>Chào mừng đến với Rạp Chiếu Phim</title>
                        <style>
                            body { margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f7f7f7; }
                            .container { max-width: 600px; margin: 20px auto; background-color: #1e1e1e; color: #ffffff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); overflow: hidden; }
                            .header { padding: 40px; text-align: center; color: #fff; font-size: 32px; font-weight: bold; }
                            .content { padding: 30px; text-align: center; }
                            .content h2 { color: #feca57; }
                            .cta-button { background-color: #ff6b6b; color: white; text-decoration: none; padding: 15px 30px; font-size: 18px; border-radius: 5px; display: inline-block; transition: background-color 0.3s; }
                            .cta-button:hover { background-color: #ee5253; }
                            .footer { background-color: #111; padding: 15px; text-align: center; font-size: 14px; color: #999; }
                            .footer a { color: #feca57; text-decoration: none; }
                        </style>
                    </head>
                    <body>
                        <div class='container'>
                            <div class='header'>🎥 Chào mừng bạn đến với Rạp Chiếu Phim của chúng tôi! 🎉</div>
                            <div class='content'>
                                <h2>Xin chào, %s!</h2>
                                <p>Cảm ơn bạn đã đăng ký thành viên! Thế giới điện ảnh tuyệt vời đang chờ đón bạn.</p>
                                <a href='https://yourcinema.com/account' class='cta-button'>Khám Phá Ngay!</a>
                            </div>
                            <div class='footer'>Nếu có thắc mắc, hãy <a href='mailto:support@yourcinema.com'>liên hệ chúng tôi</a>.</div>
                        </div>
                    </body>
                    </html>
                """, userName);
    }

}
