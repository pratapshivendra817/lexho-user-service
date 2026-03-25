package com.lexho.user_service.controller;

import com.lexho.user_service.dto.*;
import com.lexho.user_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor // 🔥 cleaner constructor
public class CartController {

    private final CartService cartService;

    // ➕ ADD
    @PostMapping
    public ApiResponse<CartResponseDTO> addToCart(@RequestBody CartRequest request) {

        Long userId = 1L; // 🔐 later JWT

        return ApiResponse.success(
                "Added to cart",
                cartService.addToCart(userId, request)
        );
    }

    // 📦 GET (UPDATED)
    @GetMapping
    public ApiResponse<CartSummaryDTO> getCart() {

        Long userId = 1L;

        return ApiResponse.success(
                "Cart fetched",
                cartService.getCart(userId) // 🔥 must return CartSummaryDTO
        );
    }

    // ❌ DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<String> remove(@PathVariable Long id) {

        return ApiResponse.success(
                cartService.removeFromCart(id),
                null
        );
    }
}