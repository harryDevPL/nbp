package com.test.npb.currency;

import com.test.npb.currency.dto.ExchangeCurrencyRequest;
import com.test.npb.currency.dto.ExchangeCurrencyResponse;

import javax.validation.Valid;

public interface CurrencyFacade {

    ExchangeCurrencyResponse getExchangeRate(final @Valid ExchangeCurrencyRequest requestDto);
}
