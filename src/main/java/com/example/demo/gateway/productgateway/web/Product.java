package com.example.demo.gateway.productgateway.web;

import lombok.Data;

@Data
public class Product {
    private final String name;

    public Product(String name) {
        this.name = name;
    }
}
