package com.example.cart_processing_unit.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.example.cart_processing_unit.model.CartItemView;
import com.example.cart_processing_unit.model.CartResponse;
import com.example.cart_processing_unit.model.ProductSnapshot;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CartService {

    private static final String PRODUCT_KEY = "flashsale:products";
    private static final String CART_PREFIX = "flashsale:cart:";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public CartResponse addToCart(String sessionId, String productId, int quantity) {
        if (quantity <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than zero");
        }

        loadProduct(productId);
        redisTemplate.opsForHash().increment(cartKey(sessionId), productId, quantity);
        return getCart(sessionId);
    }

    public CartResponse getCart(String sessionId) {
        Map<Object, Object> cartEntries = redisTemplate.opsForHash().entries(cartKey(sessionId));
        List<CartItemView> items = new ArrayList<>();

        for (Map.Entry<Object, Object> entry : cartEntries.entrySet()) {
            String productId = entry.getKey().toString();
            int quantity = Integer.parseInt(entry.getValue().toString());
            ProductSnapshot product = loadProduct(productId);
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
            items.add(CartItemView.builder()
                    .productId(productId)
                    .name(product.getName())
                    .price(product.getPrice())
                    .quantity(quantity)
                    .subtotal(subtotal)
                    .build());
        }

        items.sort(Comparator.comparing(CartItemView::getName));

        BigDecimal totalAmount = items.stream()
                .map(CartItemView::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        int totalQuantity = items.stream().mapToInt(CartItemView::getQuantity).sum();

        return CartResponse.builder()
                .sessionId(sessionId)
                .items(items)
                .totalQuantity(totalQuantity)
                .totalAmount(totalAmount)
                .build();
    }

    public void clearCart(String sessionId) {
        redisTemplate.delete(cartKey(sessionId));
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

    private String cartKey(String sessionId) {
        return CART_PREFIX + sessionId;
    }
}