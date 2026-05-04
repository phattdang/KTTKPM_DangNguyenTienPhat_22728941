package com.example.order_processing_unit.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemView {
    private String productId;
    private String name;
    private BigDecimal price;
    private int quantity;
    private BigDecimal subtotal;
}