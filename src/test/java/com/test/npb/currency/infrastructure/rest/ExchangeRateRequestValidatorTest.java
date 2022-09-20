package com.test.npb.currency.infrastructure.rest;

import com.test.npb.currency.dto.ExchangeCurrencyRequest;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
class ExchangeRateRequestValidatorTest {

    ExchangeRateRequestValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ExchangeRateRequestValidator();
    }

    @Test
    void shouldPassValidationWhenRequestIsNotNull() {
        // given
        val request = new ExchangeCurrencyRequest();

        // when
        validator.validate(request);

        // then -> no Exception is thrown
    }

    @Test
    void shouldThrowExceptionWhenRequestIsNull() {
        // when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    validator.validate(null);
                });

        // then -> IllegalArgumentException is thrown
    }
}