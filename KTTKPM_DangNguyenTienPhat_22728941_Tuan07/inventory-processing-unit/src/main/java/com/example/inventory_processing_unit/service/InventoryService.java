package com.example.inventory_processing_unit.service;

import com.example.inventory_processing_unit.model.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private static final String STOCK_KEY = "flashsale:stock";

    private final StringRedisTemplate redisTemplate;

    public StockResponse getStock(String productId) {
        Object value = redisTemplate.opsForHash().get(STOCK_KEY, productId);
        if (value == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found");
        }

        return StockResponse.builder()
                .productId(productId)
                .stock(Long.parseLong(value.toString()))
                .build();
    }

    public long decreaseStock(String productId, long quantity) {
        if (quantity <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than zero");
        }

        Object currentValue = redisTemplate.opsForHash().get(STOCK_KEY, productId);
        long currentStock = currentValue == null ? -1 : Long.parseLong(currentValue.toString());
        if (currentStock < quantity) {
            return -1;
        }

        return redisTemplate.opsForHash().increment(STOCK_KEY, productId, -quantity);
    }

    public long increaseStock(String productId, long quantity) {
        return redisTemplate.opsForHash().increment(STOCK_KEY, productId, quantity);
    }
}