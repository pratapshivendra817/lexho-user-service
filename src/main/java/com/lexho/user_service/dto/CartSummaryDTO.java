package com.lexho.user_service.dto;

import java.util.List;

public class CartSummaryDTO {

    private List<CartResponseDTO> items;
    private double totalAmount;

    // ✅ Constructor
    public CartSummaryDTO(List<CartResponseDTO> items, double totalAmount) {
        this.items = items;
        this.totalAmount = totalAmount;
    }

    // ✅ Getter
    public List<CartResponseDTO> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}



