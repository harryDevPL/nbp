package com.sygnity.npb.service;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
class NbpCurrencyResponse {

    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;
    private ResponseException responseException;

    public NbpCurrencyResponse(ResponseException responseException) {
        this.responseException = responseException;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @EqualsAndHashCode
    static class Rate {
        private String no;
        private String effectiveDate;
        private double bid;
        private double ask;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @EqualsAndHashCode
    static class ResponseException {
        private String timestamp;
        private int status;
        private String error;
        private String path;
    }

    double getRate() {
        return rates.stream()
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException("Rate not found")
                )
                .getBid();
    }
}
