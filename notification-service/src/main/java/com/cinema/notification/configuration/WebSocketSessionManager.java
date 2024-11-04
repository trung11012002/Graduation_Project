package com.cinema.notification.configuration;

import com.cinema.notification.dto.NotifyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;

    private final ConcurrentHashMap<String, String> userSessions = new ConcurrentHashMap<>();

//    public WebSocketSessionManager(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }

    // Khi user kết nối, lưu sessionId kèm theo username
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
//        String username = accessor.getUser().getName();  // Lấy username từ session hoặc JWT

//        userSessions.put(username, sessionId);
        System.out.println("User " + " connected with sessionId: " + sessionId);
        NotifyMessage notifyMessage = NotifyMessage.builder()
                .content("User connected")
                .sender("System")
                .build();
        messagingTemplate.convertAndSend("/notification-global", notifyMessage);
    }

    // Khi user ngắt kết nối, xóa sessionId khỏi danh sách
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        userSessions.values().removeIf(id -> id.equals(sessionId));
        System.out.println("Session " + sessionId + " disconnected");
    }

    // Gửi thông báo đến user cụ thể
    public void sendNotificationToUser(String username, String message) {
        String sessionId = userSessions.get(username);
        if (sessionId != null) {
            messagingTemplate.convertAndSendToUser(sessionId, "/topic/notifications", message);
        }
    }
}
