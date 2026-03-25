package com.lexho.user_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;
    private String message;

    private Boolean isRead = false;

    // 🔥 NEW FIELDS (IMPORTANT)
    private String type;        // ORDER, PAYMENT, BOOKING
    private Long referenceId;   // orderId / bookingId

    private LocalDateTime createdAt;

    // ===== GETTERS =====

    public Long getId() { return id; }

    public Long getUserId() { return userId; }

    public String getTitle() { return title; }

    public String getMessage() { return message; }

    public Boolean getIsRead() { return isRead; }

    public String getType() { return type; }

    public Long getReferenceId() { return referenceId; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    // ===== SETTERS =====

    public void setUserId(Long userId) { this.userId = userId; }

    public void setTitle(String title) { this.title = title; }

    public void setMessage(String message) { this.message = message; }

    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public void setType(String type) { this.type = type; }

    public void setReferenceId(Long referenceId) { this.referenceId = referenceId; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}