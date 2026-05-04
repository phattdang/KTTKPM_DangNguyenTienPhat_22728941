package com.example.product_processing_unit.controller;

import java.util.List;

import com.example.product_processing_unit.model.ProductView;
import com.example.product_processing_unit.service.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductCatalogService productCatalogService;

    @GetMapping
    public List<ProductView> getProducts() {
        return productCatalogService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductView getProduct(@PathVariable String id) {
        return productCatalogService.getProduct(id);
    }
}