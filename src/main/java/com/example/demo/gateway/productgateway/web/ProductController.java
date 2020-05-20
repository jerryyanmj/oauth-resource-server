package com.example.demo.gateway.productgateway.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductController  {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Value("#{ @environment['example.baseUrl'] }")
    private String serverBaseUrl;

    @Autowired
    ClientCredentialsResourceDetails oAuthDetails;

    @Autowired
    RestTemplate restTemplate;

//    @Bean
//    @ConfigurationProperties("security.oauth2.client")
//    public ClientCredentialsResourceDetails oAuthDetails(){
//
//        // provides oauth2 cleint deatils
//        ClientCredentialsResourceDetails config = new ClientCredentialsResourceDetails();
//        config.setClientId("tutorialspoint");
//        config.setClientSecret("my-secret-key");
//        config.setAccessTokenUri("http://localhost:8081/oauth/token");
//        config.setGrantType("client_credentials");
//
//        return config;
//    }

    @Bean
    @ConfigurationProperties("security.oauth2.client")
    protected ClientCredentialsResourceDetails oAuthDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @Bean
    public RestTemplate restTemplate(){
        // initialize OAuth2 Rest Client with configuration details
        MyOAuth2RestTemplate template = new MyOAuth2RestTemplate(oAuthDetails);
        template.setRetryBadAccessTokens(true);
        return template;
    }

    @GetMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    Product getProduct(){
        String url = serverBaseUrl + "/products";
        String result = restTemplate.getForObject(url, String.class);
        return new Product(result);
    }



}
