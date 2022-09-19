package com.sygnity.npb.api;

import lombok.experimental.UtilityClass;

@UtilityClass
class ExchangeRateRequestMapper {

    ExchangeRateRequestDto map(final ExchangeCurrencyRequest request) {
        return ExchangeRateRequestDto.builder()
                .sendingCurrency(request.getSendingCurrency())
                .sendingAmount(request.getSendingAmount())
                .receivingCurrency(request.getReceivingCurrency())
                .date(request.getDate())
                .build();
    }
}
