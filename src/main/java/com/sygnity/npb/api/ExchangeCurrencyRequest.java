package com.sygnity.npb.api;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
class ExchangeCurrencyRequest {

    @NotBlank @Size(min = 3, max = 3)
//    @Pattern(regexp = "[a-zA-Z]")
    String sendingCurrency;

    @NotBlank
    String sendingAmount;

    @NotBlank
    @Size(min = 3, max = 3)
//    @Pattern(regexp = "[a-zA-Z]")
    String receivingCurrency;

    @NotBlank
//    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}]")
    String date;

}
