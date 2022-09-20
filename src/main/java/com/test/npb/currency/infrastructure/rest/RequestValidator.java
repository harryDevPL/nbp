package com.test.npb.currency.infrastructure.rest;

import com.test.npb.currency.dto.ExchangeCurrencyRequest;

interface RequestValidator {

    void validate(final ExchangeCurrencyRequest request);
}
