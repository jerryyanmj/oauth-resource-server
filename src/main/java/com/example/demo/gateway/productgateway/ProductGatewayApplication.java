package com.example.demo.gateway.productgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class ProductGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductGatewayApplication.class, args);
    }

}
