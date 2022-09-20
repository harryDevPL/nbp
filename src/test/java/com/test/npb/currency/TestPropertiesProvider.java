package com.test.npb.currency;

import lombok.experimental.UtilityClass;

import java.util.stream.Stream;

@UtilityClass
class TestPropertiesProvider {

    public Stream<TestProperties> getCurrencyProviderTest() {
        return Stream.of(
                TestProperties.builder()
                        .currency("AUS")
                        .date("2022-09-20")
                        .build(),
                TestProperties.builder()
                        .currency("USD")
                        .date("2022-09-12")
                        .build(),
                TestProperties.builder()
                        .currency("CHF")
                        .date("2022-09-13")
                        .build()
        );
    }
}
