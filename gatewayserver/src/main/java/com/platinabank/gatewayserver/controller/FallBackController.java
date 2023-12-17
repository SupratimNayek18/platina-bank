package com.platinabank.gatewayserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @GetMapping("/fallbackUrl")
    public Mono<String> fallBackEndpoint(){
        return Mono.just("An error occurred. Please try after some time");
    }

}
