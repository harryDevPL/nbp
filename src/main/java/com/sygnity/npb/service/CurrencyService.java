package com.sygnity.npb.service;

import com.sygnity.npb.api.ExchangeRateRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class CurrencyService {

    public static final MathContext MATH_CONTEXT = new MathContext(4);

    CurrencyRateProvider currencyRateProvider;

    public String getExchangeRate(final ExchangeRateRequestDto requestDto) {
        val sendingCurrency = requestDto.getSendingCurrency();
        val sendingAmount = requestDto.getSendingAmount();
        val receivingCurrency = requestDto.getReceivingCurrency();
        val date = requestDto.getDate();

        val sendingCurrencyDto = currencyRateProvider.getCurrency(sendingCurrency, date);
        val receivingCurrencyDto = currencyRateProvider.getCurrency(receivingCurrency, date);

        return calculate(sendingAmount, sendingCurrencyDto.getRate(), receivingCurrencyDto.getRate());
    }

    private static String calculate(
            final String sendingAmount,
            final String sendingRate,
            final String receivingRate
    ) {
        log.info("Calculation for amount, {}, sending rate: {}, receiving rate: {}", sendingAmount, sendingRate, receivingRate);
        val amountDouble = Double.valueOf(Double.parseDouble(sendingAmount));
        val sendingRateDouble = Double.valueOf(Double.parseDouble(sendingRate));
        val receivingRateValueDouble = Double.valueOf(Double.parseDouble(receivingRate));

        return String.valueOf(
                new BigDecimal(amountDouble * sendingRateDouble / receivingRateValueDouble).round(MATH_CONTEXT)
        );
    }
}
