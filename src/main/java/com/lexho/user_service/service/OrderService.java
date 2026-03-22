package com.lexho.user_service.service;

import com.lexho.user_service.entity.Order;
import com.lexho.user_service.enums.OrderStatus;
import com.lexho.user_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    // ✅ Constructor Injection (best practice)
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // 🔹 Get Order by ID
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    // 🔹 Get all orders of a user
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // 🔥 Update Order Status with validation
    public Order updateStatus(Long orderId, OrderStatus newStatus) {

        Order order = getOrder(orderId);
        OrderStatus currentStatus = order.getStatus();

        // 🔥 Validation (core logic)
        if (!currentStatus.canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                    "Invalid status transition: " + currentStatus + " → " + newStatus
            );
        }

        order.setStatus(newStatus);

        return orderRepository.save(order);
    }

    // 🔹 Cancel Order (safe way)
    public Order cancelOrder(Long orderId) {

        Order order = getOrder(orderId);

        if (!order.getStatus().canCancel()) {
            throw new IllegalStateException("Order cannot be cancelled at this stage");
        }

        order.setStatus(OrderStatus.CANCELLED);

        return orderRepository.save(order);
    }
}