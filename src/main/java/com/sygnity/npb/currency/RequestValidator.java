package com.sygnity.npb.currency;

import com.sygnity.npb.currency.dto.ExchangeCurrencyRequest;

interface RequestValidator {

    void validate(final ExchangeCurrencyRequest request);
}
