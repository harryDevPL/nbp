package com.sygnity.npb.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
class CurrencyDto {
    private final String currencyCode;
    private final String rate;
    private final String date;
}
