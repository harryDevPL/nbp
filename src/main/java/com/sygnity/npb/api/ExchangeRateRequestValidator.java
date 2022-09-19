package com.sygnity.npb.api;

import lombok.experimental.UtilityClass;

@UtilityClass
class ExchangeRateRequestValidator {

    void validate(final ExchangeCurrencyRequest request) {
        // here can be done validation of the request if needed
        if (request == null) {
            throw new IllegalArgumentException("Request validation failed. Cause: ..." + request);
        }
    }
}
