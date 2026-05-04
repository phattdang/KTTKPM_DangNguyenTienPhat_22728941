package com.example.inventory_processing_unit.controller;

import com.example.inventory_processing_unit.model.StockResponse;
import com.example.inventory_processing_unit.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public StockResponse getStock(@PathVariable String productId) {
        return inventoryService.getStock(productId);
    }
}