package com.test.npb.currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
class InternalCurrencyDto {
    private final String currencyCode;
    private final String rate;
    private final String date;
}
