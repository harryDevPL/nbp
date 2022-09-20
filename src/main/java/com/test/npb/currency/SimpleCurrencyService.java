package com.test.npb.currency;

import com.test.npb.currency.dto.ExchangeCurrencyRequest;
import com.test.npb.currency.dto.ExchangeCurrencyResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class SimpleCurrencyService implements CurrencyService {

    CurrencyRateProvider currencyRateProvider;

    @Override
    public ExchangeCurrencyResponse getExchangeRate(final @Valid ExchangeCurrencyRequest request) {
        val sendingCurrency = request.getSendingCurrency();
        val receivingCurrency = request.getReceivingCurrency();

        val sendingCurrencyDto = currencyRateProvider.getRate(sendingCurrency, request.getDate());
        val receivingCurrencyDto = currencyRateProvider.getRate(receivingCurrency, request.getDate());

        val receivingAmount = calculate(
                request.getSendingAmount(),
                sendingCurrencyDto.getRate(),
                receivingCurrencyDto.getRate()
        );
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
