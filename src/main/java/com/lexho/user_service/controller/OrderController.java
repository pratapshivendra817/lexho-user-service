package com.lexho.user_service.controller;

import com.lexho.user_service.dto.PlaceOrderRequest;
import com.lexho.user_service.entity.Order;
import com.lexho.user_service.enums.OrderStatus;
import com.lexho.user_service.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;  // ❗ final hata diya

    // 🔥 Constructor (Spring injection)
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 🔹 Get order by id
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    // 🔹 Get user orders
    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }

    // 🔥 Update status
    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id,
                              @RequestParam OrderStatus status) {
        return orderService.updateStatus(id, status);
    }
    @PostMapping("/place")
    public Order placeOrder(@RequestBody PlaceOrderRequest request) {

        Long userId = 1L; // later JWT

        return orderService.placeOrder(userId, request);
    }

    // 🔹 Cancel order
    @PutMapping("/{id}/cancel")
    public Order cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }
}