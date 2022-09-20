package com.test.npb.currency;

import com.test.npb.currency.dto.ExchangeCurrencyRequest;

interface RequestValidator {

    void validate(final ExchangeCurrencyRequest request);
}
