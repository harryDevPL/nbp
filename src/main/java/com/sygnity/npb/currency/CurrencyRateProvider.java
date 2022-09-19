package com.sygnity.npb.currency;

interface CurrencyRateProvider {

    InternalCurrencyDto getCurrency(final String currencyCode, final String date);

}
