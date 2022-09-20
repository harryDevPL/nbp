package com.test.npb.currency;

import com.test.npb.currency.dto.ExchangeCurrencyRequest;
import com.test.npb.currency.dto.ExchangeCurrencyResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class CurrencyFacade {

    CurrencyService currencyService;

    public ExchangeCurrencyResponse getExchangeRate(final @Valid ExchangeCurrencyRequest request) {
        return currencyService.getExchangeRate(request);
    }
}
