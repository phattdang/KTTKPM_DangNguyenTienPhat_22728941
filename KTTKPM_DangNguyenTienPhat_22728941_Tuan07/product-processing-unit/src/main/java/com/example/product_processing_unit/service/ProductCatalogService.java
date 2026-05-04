package com.example.product_processing_unit.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.product_processing_unit.model.Product;
import com.example.product_processing_unit.model.ProductView;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductCatalogService {

    private static final String PRODUCT_KEY = "flashsale:products";
    private static final String STOCK_KEY = "flashsale:stock";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public void seedCatalog() {
        if (!redisTemplate.opsForHash().entries(PRODUCT_KEY).isEmpty()) {
            return;
        }

        List<ProductSeed> seeds = List.of(
                new ProductSeed("P1001", "Flash Runner Shoes", "Giay chay bo phien ban flash sale.", new BigDecimal("299000"), "https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=900&q=80", "Fashion", 120),
                new ProductSeed("P1002", "Smartwatch Pulse", "Dong ho thong minh theo doi suc khoe.", new BigDecimal("499000"), "https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=900&q=80", "Gadgets", 80),
                new ProductSeed("P1003", "Mini Bluetooth Speaker", "Loa mini am thanh lon hon kich thuoc.", new BigDecimal("189000"), "https://images.unsplash.com/photo-1546435770-a3e426bf472b?auto=format&fit=crop&w=900&q=80", "Audio", 150),
                new ProductSeed("P1004", "Gaming Mouse Pro", "Chuot choi game toc do cao.", new BigDecimal("259000"), "https://images.unsplash.com/photo-1527814050087-3793815479db?auto=format&fit=crop&w=900&q=80", "Gaming", 95),
                new ProductSeed("P1005", "Mechanical Keyboard", "Ban phim co ban phim co hoi dong.", new BigDecimal("699000"), "https://images.unsplash.com/photo-1511467687858-23d96c32e4ae?auto=format&fit=crop&w=900&q=80", "Gaming", 60));

        Map<String, String> productEntries = new LinkedHashMap<>();
        Map<String, String> stockEntries = new LinkedHashMap<>();

        seeds.forEach(seed -> {
            Product product = Product.builder()
                    .id(seed.id())
                    .name(seed.name())
                    .description(seed.description())
                    .price(seed.price())
                    .imageUrl(seed.imageUrl())
                    .category(seed.category())
                    .build();
            try {
                productEntries.put(seed.id(), objectMapper.writeValueAsString(product));
                stockEntries.put(seed.id(), String.valueOf(seed.stock()));
            } catch (IOException exception) {
                throw new IllegalStateException("Failed to seed catalog", exception);
            }
        });

        redisTemplate.opsForHash().putAll(PRODUCT_KEY, productEntries);
        redisTemplate.opsForHash().putAll(STOCK_KEY, stockEntries);
    }

    public List<ProductView> getAllProducts() {
        return redisTemplate.opsForHash().entries(PRODUCT_KEY).keySet().stream()
                .map(key -> getProduct(key.toString()))
                .toList();
    }

    public ProductView getProduct(String productId) {
        String productJson = (String) redisTemplate.opsForHash().get(PRODUCT_KEY, productId);
        if (productJson == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        try {
            Product product = objectMapper.readValue(productJson, Product.class);
            Object stockValue = redisTemplate.opsForHash().get(STOCK_KEY, productId);
            long stock = stockValue == null ? 0 : Long.parseLong(stockValue.toString());
            return ProductView.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .imageUrl(product.getImageUrl())
                    .category(product.getCategory())
                    .stock(stock)
                    .build();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read product data", exception);
        }
    }

    private record ProductSeed(String id, String name, String description, BigDecimal price, String imageUrl, String category, long stock) {
    }
}