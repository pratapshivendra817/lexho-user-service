package com.lexho.user_service.dto;

public class OrderItemDTO {

    private String partnerName; // ✅ FIXED
    private Double price;
    private Integer quantity;

    // 🔹 Getter
    public String getPartnerName() {
        return partnerName;
    }

    // 🔹 Setter
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    // 🔹 Getter
    public Double getPrice() {
        return price;
    }

    // 🔹 Setter
    public void setPrice(Double price) {
        this.price = price;
    }

    // 🔹 Getter
    public Integer getQuantity() {
        return quantity;
    }

    // 🔹 Setter
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}