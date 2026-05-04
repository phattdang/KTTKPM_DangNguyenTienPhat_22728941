package com.example.order_processing_unit.controller;

import com.example.order_processing_unit.model.CheckoutRequest;
import com.example.order_processing_unit.model.CheckoutResponse;
import com.example.order_processing_unit.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public CheckoutResponse checkout(@RequestBody CheckoutRequest request) {
        return orderService.checkout(request.getSessionId());
    }
}