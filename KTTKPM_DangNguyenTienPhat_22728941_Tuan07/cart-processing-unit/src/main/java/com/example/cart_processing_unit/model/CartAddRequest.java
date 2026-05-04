package com.example.cart_processing_unit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartAddRequest {
    private String sessionId;
    private String productId;
    private int quantity;
}