package com.sygnity.npb.currency;

interface CurrencyFetcher {

    InternalCurrencyDto fetch(final String currencyCode, final String date);
}
