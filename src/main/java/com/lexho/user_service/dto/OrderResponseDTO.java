package com.lexho.user_service.dto;

import java.util.List;

public class OrderResponseDTO {

    private Long id;
    private Double totalAmount;
    private String status;
    private String paymentStatus;
    private String address;
    private List<OrderItemDTO> items;

    // 🔹 Getter & Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // 🔹 Getter & Setter for totalAmount
    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // 🔹 Getter & Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // 🔹 Getter & Setter for paymentStatus
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // 🔹 Getter & Setter for address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // 🔹 Getter & Setter for items
    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}