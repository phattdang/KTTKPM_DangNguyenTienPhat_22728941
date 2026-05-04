package com.example.order_processing_unit.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.order_processing_unit.model.CheckoutResponse;
import com.example.order_processing_unit.model.OrderItemView;
import com.example.order_processing_unit.model.ProductSnapshot;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final String PRODUCT_KEY = "flashsale:products";
    private static final String STOCK_KEY = "flashsale:stock";
    private static final String CART_PREFIX = "flashsale:cart:";
    private static final String ORDER_KEY = "flashsale:orders";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public CheckoutResponse checkout(String sessionId) {
        Map<Object, Object> cartEntries = redisTemplate.opsForHash().entries(cartKey(sessionId));
        if (cartEntries.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }

        List<OrderItemView> items = new ArrayList<>();
        Map<String, Long> reserved = new LinkedHashMap<>();

        try {
            for (Map.Entry<Object, Object> entry : cartEntries.entrySet()) {
                String productId = entry.getKey().toString();
                int quantity = Integer.parseInt(entry.getValue().toString());
                ProductSnapshot product = loadProduct(productId);
                long updatedStock = decreaseStock(productId, quantity);
                if (updatedStock < 0) {
                    rollbackReservations(reserved);
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enough stock for " + product.getName());
                }

                reserved.put(productId, (long) quantity);
                BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
                items.add(OrderItemView.builder()
                        .productId(productId)
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(quantity)
                        .subtotal(subtotal)
                        .build());
            }
        } catch (ResponseStatusException exception) {
            throw exception;
        } catch (Exception exception) {
            rollbackReservations(reserved);
            throw exception;
        }

        BigDecimal totalAmount = items.stream()
                .map(OrderItemView::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        String orderId = UUID.randomUUID().toString();
        CheckoutResponse response = CheckoutResponse.builder()
                .orderId(orderId)
                .sessionId(sessionId)
                .status("SUCCESS")
                .items(items)
                .totalAmount(totalAmount)
                .createdAt(Instant.now().toString())
                .build();

        try {
            redisTemplate.opsForHash().put(ORDER_KEY, orderId, objectMapper.writeValueAsString(response));
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to persist order", exception);
        }

        redisTemplate.delete(cartKey(sessionId));
        return response;
    }

    private ProductSnapshot loadProduct(String productId) {
        String productJson = (String) redisTemplate.opsForHash().get(PRODUCT_KEY, productId);
        if (productJson == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        try {
            return objectMapper.readValue(productJson, ProductSnapshot.class);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read product data", exception);
        }
    }

    private long decreaseStock(String productId, long quantity) {
        Object currentValue = redisTemplate.opsForHash().get(STOCK_KEY, productId);
        long currentStock = currentValue == null ? -1 : Long.parseLong(currentValue.toString());
        if (currentStock < quantity) {
            return -1;
        }

        return redisTemplate.opsForHash().increment(STOCK_KEY, productId, -quantity);
    }

    private void rollbackReservations(Map<String, Long> reserved) {
        reserved.forEach((productId, quantity) -> redisTemplate.opsForHash().increment(STOCK_KEY, productId, quantity));
    }

    private String cartKey(String sessionId) {
        return CART_PREFIX + sessionId;
    }
}