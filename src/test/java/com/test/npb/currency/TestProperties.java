package com.test.npb.currency;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class TestProperties {

    @Builder.Default
    private String currency = "dolar amerykański";

    @Builder.Default
    private String code = "USD";

    @Builder.Default
    private String date = "2022-09-19";

    @Builder.Default
    private String table = "C";

    @Builder.Default
    private NbpCurrencyResponse.Rate rate =
            NbpCurrencyResponse.Rate.builder()
                    .no("182/C/NBP/2022")
                    .effectiveDate("2022-09-20")
                    .ask(4.7627)
                    .bid(6683)
                    .build();
}
