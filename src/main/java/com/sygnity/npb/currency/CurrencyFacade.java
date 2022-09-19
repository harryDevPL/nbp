package com.sygnity.npb.currency;

import com.sygnity.npb.currency.dto.ExchangeCurrencyRequest;
import com.sygnity.npb.currency.dto.ExchangeCurrencyResponse;

import javax.validation.Valid;

public interface CurrencyFacade {

    ExchangeCurrencyResponse getExchangeRate(final @Valid ExchangeCurrencyRequest requestDto);
}
