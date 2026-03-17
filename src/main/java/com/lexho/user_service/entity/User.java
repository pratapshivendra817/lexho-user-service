package com.lexho.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String role;

    // ✅ Getter
    public String getPhone() {
        return phone;
    }

    // ✅ Setter
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}