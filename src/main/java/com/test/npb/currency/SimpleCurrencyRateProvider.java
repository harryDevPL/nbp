package com.test.npb.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class SimpleCurrencyRateProvider implements CurrencyRateProvider {

    private static final String PATTERN_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern(PATTERN_FORMAT)
            .withZone(ZoneId.systemDefault());

    CurrencyRepository repository;
    CurrencyFetcher fetcher;

    @Override
    public InternalCurrencyDto getRate(final String currencyCode, final String date) {
        val currencyOptional = repository.findByCurrencyCodeAndDate(currencyCode, date);

        return mapToDto(currencyOptional.orElseGet(
                () -> getCurrencyFromNbp(currencyCode, date)
        ));
    }

    private CurrencyEntity getCurrencyFromNbp(final String currencyCode, final String date) {
        log.info("Currency: {} not found id database for date: {}", currencyCode, date);

        val currencyDto = fetcher.fetch(currencyCode, date);
        log.info("Currency: {} rate fetched from NBP API", currencyCode);

        return repository.saveAndFlush(mapToEntity(currencyDto));
    }

    private CurrencyEntity mapToEntity(InternalCurrencyDto internalCurrencyDto) {
        return CurrencyEntity.builder()
                .currencyCode(internalCurrencyDto.getCurrencyCode())
                .rate(internalCurrencyDto.getRate())
                .date(DATE_TIME_FORMATTER.format(Instant.now()))
                .build();
    }

    private InternalCurrencyDto mapToDto(final CurrencyEntity currencyEntity) {
        return InternalCurrencyDto.builder()
                .currencyCode(currencyEntity.getCurrencyCode())
                .rate(currencyEntity.getRate())
                .date(currencyEntity.getDate())
                .build();
    }
}
