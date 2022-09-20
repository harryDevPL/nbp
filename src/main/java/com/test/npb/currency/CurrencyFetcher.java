package com.test.npb.currency;

import java.util.concurrent.ThreadLocalRandom;

interface CurrencyFetcher {

    InternalCurrencyDto fetch(final String currencyCode, final String date);
}

class DummyCurrencyFetcher implements CurrencyFetcher {

    @Override
    public InternalCurrencyDto fetch(final String currencyCode, final String date) {
        return InternalCurrencyDto.builder()
                .rate(String.valueOf(getRandomRate()))
                .currencyCode(currencyCode)
                .build();
    }

    private double getRandomRate() {
        return ThreadLocalRandom.current().nextDouble(0.25, 7.45);
    }
}
