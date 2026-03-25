package com.lexho.user_service.controller;


import com.lexho.user_service.entity.Notification;
import com.lexho.user_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 📦 GET ALL
    @GetMapping("/{userId}")
    public List<Notification> getAll(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    // 🔄 MARK READ
    @PutMapping("/{id}/read")
    public String markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return "Marked as read";
    }
}