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

    // only for test purpose! do not use it on production!
    CurrencyRepository inMemoryCurrencyRepository() {
        return new InMemoryCurrencyRepository();
    }

    // only for test purpose! do not use it on production!
    CurrencyFetcher dummyCurrencyFetcher() {
        return new DummyCurrencyFetcher();
    }

    @Bean
    ClientProperties nbpProperties() {
        return new NbpProperties();
    }

    @Bean
    CurrencyFacade currencyFacade(final CurrencyService service) {
        return new CurrencyFacade(service);
    }

    @Bean
    CurrencyRateProvider currencyRateProvider(final CurrencyRepository repository, final CurrencyFetcher fetcher) {
        return new SimpleCurrencyRateProvider(repository, fetcher);
    }

    @Bean
    CurrencyFetcher currencyFetcher(final WebClient webClient, final ClientProperties properties) {
        return new NbpCurrencyFetcher(webClient, properties);
    }

    @Bean
    CurrencyService currencyService(final CurrencyRateProvider rateProvider) {
        return new SimpleCurrencyService(rateProvider);
    }


    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
