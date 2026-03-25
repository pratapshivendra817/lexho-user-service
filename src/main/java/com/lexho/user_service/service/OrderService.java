package com.lexho.user_service.service;

import com.lexho.user_service.dto.PlaceOrderRequest;
import com.lexho.user_service.entity.Cart;
import com.lexho.user_service.entity.Order;
import com.lexho.user_service.entity.OrderItem;
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

    // 🔹 Get Order by ID
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    // 🔹 Get all orders of a user
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // 🔥 STATUS UPDATE
    public Order updateStatus(Long orderId, OrderStatus newStatus) {

        Order order = getOrder(orderId);
        OrderStatus currentStatus = order.getStatus();

        if (!currentStatus.canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                    "Invalid status transition: " + currentStatus + " → " + newStatus
            );
        }

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    // 🔹 CANCEL
    public Order cancelOrder(Long orderId) {

        Order order = getOrder(orderId);

        if (!order.getStatus().canCancel()) {
            throw new IllegalStateException("Order cannot be cancelled at this stage");
        }

        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    // 🔥🔥 MAIN LOGIC (Cart → Order)
    @Transactional
    public Order placeOrder(Long userId, PlaceOrderRequest request) {

        // 1. Get cart items
        List<Cart> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 2. Create Order
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CREATED);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setAddress(request.getAddress());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        // 3. Convert cart → order items
        for (Cart cart : cartItems) {

            OrderItem item = new OrderItem();
            item.setProductId(cart.getPartnerId());
            item.setProductName(cart.getPartnerName());
            item.setPrice(cart.getPrice());
            item.setQuantity(cart.getQuantity());
            item.setOrder(order);

            orderItems.add(item);

            total += cart.getPrice() * cart.getQuantity();
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        // 4. Save Order
        Order savedOrder = orderRepository.save(order);

        // 5. Clear Cart
        cartRepository.deleteAll(cartItems);

        return savedOrder;
    }
}