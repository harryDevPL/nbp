package com.test.npb.currency;

interface CurrencyProvider {

    InternalCurrencyDto provide(final String currencyCode, final String date);
}
