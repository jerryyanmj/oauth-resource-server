package com.example.demo.gateway.productgateway.web;

public class CustomClient extends ApiBinding {

    private static final String CLIENT_BASE_URL = "http://localhost:8080/";

    public CustomClient(String accessToken) {
        super(accessToken);
    }

    public String getProduct() {
        return restTemplate.getForObject(CLIENT_BASE_URL + "/products", String.class);
    }
}
