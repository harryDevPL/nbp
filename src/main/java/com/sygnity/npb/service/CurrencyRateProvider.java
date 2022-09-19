package com.sygnity.npb.service;

import com.sygnity.npb.model.Currency;
import com.sygnity.npb.repository.CurrencyRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
class CurrencyRateProvider {

    private static final String PATTERN_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern(PATTERN_FORMAT)
            .withZone(ZoneId.systemDefault());

    CurrencyRepository repository;
    NbpCurrencyFetcher fetcher;

    CurrencyDto getCurrency(
            final String currencyCode,
            final String date
    ) {
        val currencyOptional = repository.findByCurrencyCodeAndDate(currencyCode, date);

        if (currencyOptional.isPresent()) {
            log.info(String.format("Currency: %s found id database", currencyCode));
            return mapToDto(currencyOptional.get());
        } else {
            log.info(String.format("Currency: %s not found id database", currencyCode));
            return getCurrencyFromNbp(currencyCode, date);
        }
    }

    private CurrencyDto getCurrencyFromNbp(String currencyCode, String date) {
        val currencyDto = fetcher.fetch(currencyCode, date);
        log.info(String.format("Currency: %s rate fetched from NBP API", currencyCode));

        repository.save(mapToEntity(currencyDto));
        return currencyDto;
    }

    private static Currency mapToEntity(CurrencyDto currencyDto) {
        return Currency.builder()
                .currencyCode(currencyDto.getCurrencyCode())
                .rate(currencyDto.getRate())
                .date(DATE_TIME_FORMATTER.format(Instant.now()))
                .build();
    }

    private static CurrencyDto mapToDto(final Currency currency) {
        return CurrencyDto.builder()
                .currencyCode(currency.getCurrencyCode())
                .rate(currency.getRate())
                .date(currency.getDate())
                .build();
    }
}
