package com.sygnity.npb.currency;

import com.sygnity.npb.currency.dto.ExchangeCurrencyRequest;
import com.sygnity.npb.currency.dto.ExchangeCurrencyResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
class SimpleCurrencyFacade implements CurrencyFacade {

    CurrencyRateProvider currencyRateProvider;

    RequestValidator validator;

    @Override
    public ExchangeCurrencyResponse getExchangeRate(final @Valid ExchangeCurrencyRequest request) {
        validator.validate(request);

        val sendingCurrency = request.getSendingCurrency();
        val sendingAmount = request.getSendingAmount();
        val receivingCurrency = request.getReceivingCurrency();
        val date = request.getDate();

        val sendingCurrencyDto = currencyRateProvider.getCurrency(sendingCurrency, date);
        val receivingCurrencyDto = currencyRateProvider.getCurrency(receivingCurrency, date);

        val receivingAmount = calculate(sendingAmount, sendingCurrencyDto.getRate(), receivingCurrencyDto.getRate());
        return new ExchangeCurrencyResponse(receivingCurrency, receivingAmount);
    }

    private String calculate(
            final String sendingAmount,
            final String sendingRate,
            final String receivingRate
    ) {
        log.info("Calculation for amount, {}, sending rate: {}, receiving rate: {}", sendingAmount, sendingRate, receivingRate);
        val amountDouble = Double.valueOf(Double.parseDouble(sendingAmount));
        val sendingRateDouble = Double.valueOf(Double.parseDouble(sendingRate));
        val receivingRateValueDouble = Double.valueOf(Double.parseDouble(receivingRate));

        return new BigDecimal(amountDouble * sendingRateDouble / receivingRateValueDouble)
                .setScale(2, RoundingMode.HALF_UP).toString();
    }
}
