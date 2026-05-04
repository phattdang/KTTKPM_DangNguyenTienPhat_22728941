package com.example.cart_processing_unit.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private String sessionId;
    private List<CartItemView> items;
    private int totalQuantity;
    private BigDecimal totalAmount;
}