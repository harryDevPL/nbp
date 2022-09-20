package com.test.npb.currency;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
class NbpCurrencyProvider implements CurrencyProvider {

    private static final String SLASH = "/";
    private static final String TABLE_C = "c";
    private static final String EXCEPTION_MESSAGE_PATTERN = "Currency: %s or date: %s not valid";
    WebClient webClient;
    ClientProperties properties;

    @Override
    public InternalCurrencyDto provide(final String currencyCode, final String date) {
        log.info("Fetching {} from {}", currencyCode, date);
        try {
            val response = webClient.get()
                    .uri(createPath(currencyCode, date))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(EXCEPTION_MESSAGE_PATTERN, currencyCode, date));
                    })
                    .bodyToMono(NbpCurrencyResponse.class)
                    .block();
            return mapResponseToCurrency(response);
        } catch (final ResponseStatusException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format(EXCEPTION_MESSAGE_PATTERN, currencyCode, date)
            );
        }
    }

    private InternalCurrencyDto mapResponseToCurrency(final NbpCurrencyResponse response) {
        if (response == null) {
            throw new IllegalArgumentException("Nbp response is null. Trace id: ...");
        }
        return InternalCurrencyDto.builder()
                .rate(String.valueOf(response.getRate()))
                .currencyCode(response.getCode())
                .build();
    }

    private String createPath(final String currencyCode, final String date) {
        return properties.getBaseUrl() + SLASH + TABLE_C + SLASH + currencyCode + SLASH + date;
    }
}
