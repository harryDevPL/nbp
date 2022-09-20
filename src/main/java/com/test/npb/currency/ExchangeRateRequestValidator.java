package com.test.npb.currency;

import com.test.npb.currency.dto.ExchangeCurrencyRequest;

class ExchangeRateRequestValidator implements RequestValidator {

    public void validate(final ExchangeCurrencyRequest request) {
        // here can be done validation of the request if needed
        // e.g. if exchange date is working day or
        // if it should be changed to previous working day if it's not working day
        if (request == null) {
            throw new IllegalArgumentException("Request validation failed. Cause: ..." + request);
        }
    }
}
