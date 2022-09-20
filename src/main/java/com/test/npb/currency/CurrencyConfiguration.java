package com.test.npb.currency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableJpaRepositories
class CurrencyConfiguration {

    @Bean
    ClientProperties nbpProperties() {
        return new NbpProperties();
    }

    @Bean
    CurrencyFacade currencyFacade(CurrencyRateProvider rateProvider, RequestValidator validator) {
        return new SimpleCurrencyFacade(rateProvider, validator);
    }

    @Bean
    CurrencyRateProvider currencyRateProvider(CurrencyRepository repository, CurrencyProvider fetcher) {
        return new SimpleCurrencyRateProvider(repository, fetcher);
    }

    @Bean
    CurrencyProvider currencyFetcher(WebClient webClient, ClientProperties properties) {
        return new NbpCurrencyProvider(webClient, properties);
    }

    @Bean
    RequestValidator requestValidator() {
        return new ExchangeRateRequestValidator();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
