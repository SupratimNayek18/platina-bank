package com.platinabank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator platinaBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/platinabank/platinabank-accounts/**")
                        .filters(f -> f.rewritePath("/platinabank/platinabank-accounts/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("platinabankAccountsCircuitBreaker")
                                        .setFallbackUri("forward:/fallbackUrl")))
                        .uri("lb://PLATINABANK-ACCOUNTS"))
                .route(p -> p
                        .path("/platinabank/platinabank-loans/**")
                        .filters(f -> f.rewritePath("/platinabank/platinabank-loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
                        .uri("lb://PLATINABANK-LOANS"))
                .route(p -> p
                        .path("/platinabank/platinabank-cards/**")
                        .filters(f -> f.rewritePath("/platinabank/platinabank-cards/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://PLATINABANK-CARDS"))
                .build();
    }
}
