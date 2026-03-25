package com.lexho.user_service.service;

import com.lexho.user_service.dto.CartRequest;
import com.lexho.user_service.dto.CartResponseDTO;
import com.lexho.user_service.dto.CartSummaryDTO; // ✅ IMPORTANT
import com.lexho.user_service.entity.Cart;
import com.lexho.user_service.entity.Partner;
import com.lexho.user_service.repository.CartRepository;
import com.lexho.user_service.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final PartnerRepository partnerRepo;

    // ➕ ADD TO CART (SMART + SAFE)
    @Transactional
    public CartResponseDTO addToCart(Long userId, CartRequest request) {

        // ✅ VALIDATION
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        // ✅ FETCH PARTNER
        Partner partner = partnerRepo.findById(request.getPartnerId())
                .orElseThrow(() -> new RuntimeException("Partner not found"));

        // ✅ CHECK EXISTING
        Cart cart = cartRepo
                .findByUserIdAndPartnerId(userId, partner.getId())
                .orElseGet(Cart::new);

        // ✅ SET DATA
        if (cart.getId() == null) {
            // NEW CART
            cart.setUserId(userId);
            cart.setPartnerId(partner.getId());
            cart.setQuantity(request.getQuantity());
        } else {
            // UPDATE CART (MERGE)
            cart.setQuantity(cart.getQuantity() + request.getQuantity());
        }

        // ✅ ALWAYS UPDATE LATEST DATA
        cart.setPartnerName(partner.getName());
        cart.setPrice(partner.getPrice());

        // ✅ SAVE
        Cart saved = cartRepo.save(cart);

        return new CartResponseDTO(
                saved.getId(),
                saved.getPartnerId(),
                saved.getPartnerName(),
                saved.getPrice(),
                saved.getQuantity()
        );
    }

    // 📦 GET CART (FINAL FIXED)
    public CartSummaryDTO getCart(Long userId) {

        List<Cart> cartList = cartRepo.findByUserId(userId);

        // 🔥 TOTAL CALCULATION
        double total = cartList.stream()
                .mapToDouble(c -> c.getPrice() * c.getQuantity())
                .sum();

        // 🔥 ITEM LIST
        List<CartResponseDTO> items = cartList.stream()
                .map(cart -> new CartResponseDTO(
                        cart.getId(),
                        cart.getPartnerId(),
                        cart.getPartnerName(),
                        cart.getPrice(),
                        cart.getQuantity()
                ))
                .toList();

        return new CartSummaryDTO(items, total);
    }

    // ❌ REMOVE
    public String removeFromCart(Long cartId) {

        if (!cartRepo.existsById(cartId)) {
            throw new RuntimeException("Cart item not found");
        }

        cartRepo.deleteById(cartId);
        return "Removed successfully";
    }
}