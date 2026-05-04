package com.example.product_processing_unit.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductView {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private String category;
    private long stock;
}