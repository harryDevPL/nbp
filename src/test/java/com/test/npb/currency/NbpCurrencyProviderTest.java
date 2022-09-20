package com.test.npb.currency;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NbpCurrencyProviderTest {

    @Mock
    WebClient webClientMock;

    @Mock
    ClientProperties propertiesMock;

    @SuppressWarnings("rawtypes")
    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @SuppressWarnings("rawtypes")
    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @SuppressWarnings("rawtypes")
    @Mock
    WebClient.ResponseSpec responseSpecMock;

    @SuppressWarnings("rawtypes")
    @Mock
    WebClient.ResponseSpec responseSpecOnStatusMock;

    @Mock
    Mono<NbpCurrencyResponse> nbpCurrencyResponseMonoMock;

    CurrencyProvider provider;

    @BeforeEach
    void setUp() {
        provider = new NbpCurrencyProvider(webClientMock, propertiesMock);
    }

    @ParameterizedTest
    @MethodSource("getHappyPathProperties")
    void should(final TestProperties testProperties) {
        // given
        val expectedCurrencyCode = testProperties.getCode();
        val expectedCurrencyDto = new NbpCurrencyResponse(
                testProperties.getTable(),
                testProperties.getCurrency(),
                testProperties.getCode(),
                List.of(testProperties.getRate())
        );

        // when
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.onStatus(any(), any())).thenReturn(responseSpecOnStatusMock);
        when(responseSpecOnStatusMock.bodyToMono(NbpCurrencyResponse.class)).thenReturn(nbpCurrencyResponseMonoMock);
        when(nbpCurrencyResponseMonoMock.block()).thenReturn(expectedCurrencyDto);

        val currencyDto = provider.provide(expectedCurrencyCode, testProperties.getDate());

        // then
        val actualCurrencyCode = currencyDto.getCurrencyCode();
        Assertions.assertThat(actualCurrencyCode).isEqualTo(expectedCurrencyCode);
    }

    private static Stream<TestProperties> getHappyPathProperties() {
        return TestPropertiesProvider.getCurrencyProviderTest();
    }
}