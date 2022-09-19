package com.sygnity.npb.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExchangeRateRequestDto {
    String sendingCurrency;
    String sendingAmount;
    String receivingCurrency;
    String date;
}
