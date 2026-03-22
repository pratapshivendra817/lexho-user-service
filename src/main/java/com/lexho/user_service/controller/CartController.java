package com.lexho.user_service.controller;

import com.lexho.user_service.dto.*;
import com.lexho.user_service.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ➕ ADD
    @PostMapping
    public ApiResponse<CartResponseDTO> addToCart(@RequestBody CartRequest request) {

        Long userId = 1L; // 🔐 later JWT

        return ApiResponse.success(
                "Added to cart",
                cartService.addToCart(userId, request)
        );
    }

    // 📦 GET
    @GetMapping
    public ApiResponse<List<CartResponseDTO>> getCart() {

        Long userId = 1L;

        return ApiResponse.success(
                "Cart fetched",
                cartService.getCart(userId)
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