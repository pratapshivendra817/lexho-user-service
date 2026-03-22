package com.lexho.user_service.dto;


public class CartResponseDTO {

    private Long cartId;
    private Long partnerId;
    private String partnerName;
    private Double price;
    private Integer quantity;

    public CartResponseDTO(Long cartId, Long partnerId, String partnerName, Double price, Integer quantity) {
        this.cartId = cartId;
        this.partnerId = partnerId;
        this.partnerName = partnerName;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getCartId() { return cartId; }
    public Long getPartnerId() { return partnerId; }
    public String getPartnerName() { return partnerName; }
    public Double getPrice() { return price; }
    public Integer getQuantity() { return quantity; }
}
