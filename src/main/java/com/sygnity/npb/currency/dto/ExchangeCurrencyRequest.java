package com.sygnity.npb.currency.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public class ExchangeCurrencyRequest {

    @NotBlank @Size(min = 3, max = 3)
    @Pattern(regexp = "[a-zA-Z]{3}")
    String sendingCurrency;

    @NotBlank
    String sendingAmount;

    @NotBlank
    @Size(min = 3, max = 3)
    @Pattern(regexp = "[a-zA-Z]{3}")
    String receivingCurrency;

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    String date;

}
