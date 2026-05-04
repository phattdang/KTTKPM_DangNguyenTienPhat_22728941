package com.example.order_processing_unit.model;

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
public class CheckoutResponse {
    private String orderId;
    private String sessionId;
    private String status;
    private List<OrderItemView> items;
    private BigDecimal totalAmount;
    private String createdAt;
}