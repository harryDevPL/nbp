package com.test.npb.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class ExchangeCurrencyResponse {

    private final String currency;
    private final String value;
}
