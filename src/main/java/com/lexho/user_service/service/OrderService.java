package com.lexho.user_service.service;

import com.lexho.user_service.dto.*;
import com.lexho.user_service.entity.*;
import com.lexho.user_service.enums.OrderStatus;
import com.lexho.user_service.enums.PaymentStatus;
import com.lexho.user_service.repository.CartRepository;
import com.lexho.user_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    // 🔹 Get Order
    public OrderResponseDTO getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return mapToDTO(order);
    }

    // 🔹 Get user orders
    public List<OrderResponseDTO> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        List<OrderResponseDTO> list = new ArrayList<>();
        for (Order o : orders) {
            list.add(mapToDTO(o));
        }
        return list;
    }

    // 🔥 PLACE ORDER
    @Transactional
    public OrderResponseDTO placeOrder(Long userId, PlaceOrderRequest request) {

        List<Cart> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CREATED);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setAddress(request.getAddress());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (Cart cart : cartItems) {

            // ✅ VALIDATION
            if (cart.getPrice() == null) {
                throw new RuntimeException("Cart price missing for cartId: " + cart.getId());
            }

            if (cart.getQuantity() == null || cart.getQuantity() <= 0) {
                throw new RuntimeException("Invalid quantity for cartId: " + cart.getId());
            }

            OrderItem item = new OrderItem();

            // 🔥 FIXED (CONSISTENT NAMING)
            item.setPartnerId(cart.getPartnerId());
            item.setPartnerName(cart.getPartnerName());

            item.setPrice(cart.getPrice());
            item.setQuantity(cart.getQuantity());
            item.setOrder(order);

            orderItems.add(item);

            total += cart.getPrice() * cart.getQuantity();
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        // 🔥 CLEAR CART
        cartRepository.deleteAll(cartItems);

        return mapToDTO(savedOrder);
    }

    // 🔥 STATUS UPDATE
    public OrderResponseDTO updateStatus(Long orderId, OrderStatus newStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        if (!order.getStatus().canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                    "Invalid status transition: " + order.getStatus() + " → " + newStatus
            );
        }

        order.setStatus(newStatus);

        return mapToDTO(orderRepository.save(order));
    }

    // 🔹 CANCEL ORDER
    public OrderResponseDTO cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        if (!order.getStatus().canCancel()) {
            throw new IllegalStateException("Order cannot be cancelled at this stage");
        }

        order.setStatus(OrderStatus.CANCELLED);

        return mapToDTO(orderRepository.save(order));
    }

    // 🔥 DTO MAPPER (FINAL SAFE VERSION)
    public OrderResponseDTO mapToDTO(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().name());

        // ✅ NULL SAFE
        dto.setPaymentStatus(
                order.getPaymentStatus() != null
                        ? order.getPaymentStatus().name()
                        : "PENDING"
        );

        dto.setAddress(order.getAddress());

        List<OrderItemDTO> items = new ArrayList<>();

        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {

                OrderItemDTO i = new OrderItemDTO();

                // 🔥 FIXED NAMING
                i.setPartnerName(item.getPartnerName());
                i.setPrice(item.getPrice());
                i.setQuantity(item.getQuantity());

                items.add(i);
            }
        }

        dto.setItems(items);

        return dto;
    }
}