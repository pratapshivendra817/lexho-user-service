package com.lexho.user_service.service;

import com.lexho.user_service.dto.CartRequest;
import com.lexho.user_service.dto.CartResponseDTO;
import com.lexho.user_service.entity.Cart;
import com.lexho.user_service.entity.Partner;
import com.lexho.user_service.repository.CartRepository;
import com.lexho.user_service.repository.PartnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepo;
    private final PartnerRepository partnerRepo;

    public CartService(CartRepository cartRepo, PartnerRepository partnerRepo) {
        this.cartRepo = cartRepo;
        this.partnerRepo = partnerRepo;
    }

    // ➕ ADD TO CART (with duplicate + quantity logic)
    public CartResponseDTO addToCart(Long userId, CartRequest request) {

        Partner partner = partnerRepo.findById(request.getPartnerId())
                .orElseThrow(() -> new RuntimeException("Partner not found"));

        Cart cart = cartRepo.findByUserIdAndPartnerId(userId, request.getPartnerId())
                .orElse(null);

        if (cart != null) {
            // 🔥 already exists → increase quantity
            cart.setQuantity(cart.getQuantity() + request.getQuantity());
        } else {
            // 🔥 new item
            cart = new Cart();
            cart.setUserId(userId);
            cart.setPartnerId(request.getPartnerId());
            cart.setQuantity(request.getQuantity());
        }

        Cart saved = cartRepo.save(cart);

        return new CartResponseDTO(
                saved.getId(),
                partner.getId(),
                partner.getName(),
                partner.getPrice(),
                saved.getQuantity()
        );
    }

    // 📦 GET CART
    public List<CartResponseDTO> getCart(Long userId) {

        return cartRepo.findByUserId(userId)
                .stream()
                .map(cart -> {
                    Partner p = partnerRepo.findById(cart.getPartnerId()).orElse(null);

                    return new CartResponseDTO(
                            cart.getId(),
                            cart.getPartnerId(),
                            p != null ? p.getName() : "N/A",
                            p != null ? p.getPrice() : 0.0,
                            cart.getQuantity()
                    );
                })
                .toList();
    }

    // ❌ REMOVE
    public String removeFromCart(Long cartId) {
        cartRepo.deleteById(cartId);
        return "Removed successfully";
    }
}