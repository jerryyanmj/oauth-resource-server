package com.example.demo.gateway.productgateway.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class ProductController extends ApiBinding {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OAuth2AuthorizedClientService clientService;



    public ProductController() {
        super(null);
    }



    @GetMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    Product getProduct(){
//        Authentication authentication = SecurityContextHolder
//                        .getContext()
//                        .getAuthentication();
//
//        OAuth2AuthenticationToken oauthToken =
//                (OAuth2AuthenticationToken) authentication;
//
//
//        OAuth2AuthorizedClient client =
//
//                clientService.loadAuthorizedClient(
//                        oauthToken.getAuthorizedClientRegistrationId(),
//                        oauthToken.getName());
//
//
//
//        String accessToken = client.getAccessToken().getTokenValue();


        //ClientRegistration registration = ClientRegistration.withRegistrationId("messaging-client-client-creds").build();

        //logger.info("Registration: {}", registration.toString());



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("CLASS: {}", authentication.getClass().getCanonicalName());
        String accessToken = null;
        if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
            logger.info("GETTING TOKEN");
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            if (clientRegistrationId.equals("my-server")) {
                OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());
                accessToken = client.getAccessToken().getTokenValue();
            }
        }

        //OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, )

        //OAuth2AuthorizedClient client2 =new OAuth2AuthorizedClient(registration, "principal", )

        Mono<String> mono = webClient.get().uri("products").retrieve().bodyToMono(String.class);

        return new Product(mono.toString());

        //return new Product(new CustomClient(accessToken).getProduct());
    }

    @Autowired
    WebClient webClient;

    @Bean
    WebClient webClient(ClientRegistrationRepository clientRegistrations,
                        OAuth2AuthorizedClientRepository authorizedClients) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, authorizedClients);
        // (optional) explicitly opt into using the oauth2Login to provide an access token implicitly
        oauth2.setDefaultOAuth2AuthorizedClient(true);
        // (optional) set a default ClientRegistration.registrationId
        oauth2.setDefaultClientRegistrationId("my-server");
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .apply(oauth2.oauth2Configuration())
                .build();
    }


    @Bean
    RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate;
    }

}
