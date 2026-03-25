package com.lexho.user_service.controller;

import com.lexho.user_service.dto.*;
import com.lexho.user_service.enums.OrderStatus;
import com.lexho.user_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ApiResponse<OrderResponseDTO> getOrder(@PathVariable Long id) {
        return ApiResponse.success("Order fetched", orderService.getOrder(id));
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<OrderResponseDTO>> getUserOrders(@PathVariable Long userId) {
        return ApiResponse.success("Orders fetched", orderService.getUserOrders(userId));
    }

    @PostMapping("/place")
    public ApiResponse<OrderResponseDTO> placeOrder(@RequestBody PlaceOrderRequest request) {
        return ApiResponse.success("Order placed", orderService.placeOrder(1L, request));
    }

    @PutMapping("/{id}/status")
    public ApiResponse<OrderResponseDTO> updateStatus(@PathVariable Long id,
                                                      @RequestParam OrderStatus status) {
        return ApiResponse.success("Status updated", orderService.updateStatus(id, status));
    }

    @PutMapping("/{id}/cancel")
    public ApiResponse<OrderResponseDTO> cancelOrder(@PathVariable Long id) {
        return ApiResponse.success("Order cancelled", orderService.cancelOrder(id));
    }
}