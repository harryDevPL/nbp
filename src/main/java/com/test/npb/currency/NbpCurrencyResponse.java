package com.test.npb.currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class NbpCurrencyResponse {

    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Rate {
        private String no;
        private String effectiveDate;
        private double bid;
        private double ask;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
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
