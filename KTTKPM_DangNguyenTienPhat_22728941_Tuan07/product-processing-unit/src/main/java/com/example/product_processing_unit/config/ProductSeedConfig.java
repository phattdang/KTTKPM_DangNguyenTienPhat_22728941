package com.example.product_processing_unit.config;

import com.example.product_processing_unit.service.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProductSeedConfig {

    private final ProductCatalogService productCatalogService;

    @Bean
    public ApplicationRunner seedProducts() {
        return args -> productCatalogService.seedCatalog();
    }
}