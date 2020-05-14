package com.example.demo.gateway.productgateway.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductController extends ApiBinding {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OAuth2AuthorizedClientService clientService;

    public ProductController(String accessToken) {
        super(accessToken);
    }

    @GetMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    Product getProduct(){
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        OAuth2AuthenticationToken oauthToken =
                (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient client =
                clientService.loadAuthorizedClient(
                        oauthToken.getAuthorizedClientRegistrationId(),
                        oauthToken.getName());

        String accessToken = client.getAccessToken().getTokenValue();


        return new Product("Apple");
    }

    @Bean
    RestTemplate getRestTemplate(){

        return null;
    }

}
