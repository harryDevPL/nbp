package com.test.npb.currency.infrastructure.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RestConfiguration {

    @Bean
    RequestValidator requestValidator() {
        return new ExchangeRateRequestValidator();
    }
}
