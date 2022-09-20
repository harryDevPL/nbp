package com.test.npb.currency;

interface CurrencyRateProvider {

    InternalCurrencyDto getRate(final String currencyCode, final String date);

}
