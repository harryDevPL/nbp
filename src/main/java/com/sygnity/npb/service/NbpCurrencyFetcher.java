package com.sygnity.npb.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
@Slf4j
class NbpCurrencyFetcher {

    private static final String SLASH = "/";
    private static final String TABLE_C = "c";
    WebClient webClient;
    NbpProperties properties;

    public CurrencyDto fetch(final String currencyCode, final String date) {
        log.info("Fetching {} from {}", currencyCode, date);
        try {
            val response = webClient.get()
                    .uri(createPath(currencyCode, date))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                        return Mono.error(new ResponseStatusException(
                                HttpStatus.NOT_FOUND, String.format("Currency2: %s or date: %s not valid", currencyCode, date)
                        ));
                    })
                    .bodyToMono(NbpCurrencyResponse.class)
                    .onErrorMap(throwable -> {
                        return new ResponseStatusException(
                                HttpStatus.NOT_FOUND, String.format("Currency3: %s or date: %s not valid", currencyCode, date)
                        );
                    })
                    .block();
            return mapResponseToCurrency(response);
        } catch (final ResponseStatusException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Currency: %s or date: %s not valid", currencyCode, date)
            );
        }
    }

    private CurrencyDto mapResponseToCurrency(final NbpCurrencyResponse response) {
        if (response == null) {
            throw new IllegalArgumentException("Nbp response is null. Trace id: ...");
        }
        return CurrencyDto.builder()
                .rate(String.valueOf(response.getRate()))
                .currencyCode(response.getCode())
                .build();
    }

    private String createPath(String currencyCode, String date) {
        return properties.getBaseUrl() + SLASH + TABLE_C + SLASH + currencyCode + SLASH + date;
    }
}
