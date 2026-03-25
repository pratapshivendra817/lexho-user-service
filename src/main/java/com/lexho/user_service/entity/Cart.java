package com.lexho.user_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long partnerId;
    private String partnerName;   // 🔥 ADD THIS
    private Double price;         // 🔥 ADD THIS
    private Integer quantity;
}