package com.example.cart_processing_unit.controller;

import com.example.cart_processing_unit.model.CartAddRequest;
import com.example.cart_processing_unit.model.CartResponse;
import com.example.cart_processing_unit.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public CartResponse addToCart(@RequestBody CartAddRequest request) {
        int quantity = request.getQuantity() <= 0 ? 1 : request.getQuantity();
        return cartService.addToCart(request.getSessionId(), request.getProductId(), quantity);
    }

    @GetMapping
    public CartResponse getCart(@RequestParam String sessionId) {
        return cartService.getCart(sessionId);
    }
}