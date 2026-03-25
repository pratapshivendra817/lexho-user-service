package com.lexho.user_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 FIXED FIELDS
    private Long partnerId;
    private String partnerName;

    private Double price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}