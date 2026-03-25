package com.lexho.user_service.service;


import com.lexho.user_service.entity.Notification;
import com.lexho.user_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepo;

    // 🔔 SEND
    public void send(Long userId, String title, String message, String type, Long refId) {

        Notification n = new Notification();
        n.setUserId(userId);
        n.setTitle(title);
        n.setMessage(message);
        n.setType(type);
        n.setReferenceId(refId);
        n.setIsRead(false);
        n.setCreatedAt(LocalDateTime.now());

        notificationRepo.save(n);
    }

    // 📦 GET
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // 🔄 MARK AS READ
    public void markAsRead(Long id) {
        Notification n = notificationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        n.setIsRead(true);
        notificationRepo.save(n);
    }
}