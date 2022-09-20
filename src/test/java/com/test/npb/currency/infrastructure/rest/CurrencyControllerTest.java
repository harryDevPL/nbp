package com.test.npb.currency.infrastructure.rest;

import com.test.npb.AbstractSpringTest;
import com.test.npb.currency.CurrencyFacade;
import com.test.npb.currency.dto.ExchangeCurrencyRequest;
import com.test.npb.currency.dto.ExchangeCurrencyResponse;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CurrencyControllerTest extends AbstractSpringTest {

    @MockBean
    CurrencyFacade facade;

    @Test
    void shouldReturnHappyPathResponseWhenRequestContainsValidData() throws Exception {
        // given
        val exchangeCurrencyRequest = new ExchangeCurrencyRequest("USD", "100", "EUR", "2022-09-09");
        val requestAsString = objectMapper.writeValueAsString(exchangeCurrencyRequest);

        // when
        when(facade.getExchangeRate(any())).thenReturn(new ExchangeCurrencyResponse("EUR", "1.01"));

        val mvcResult = mockMvc.perform(
                        post("http://localhost:8080/api/v1/exchange/")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestAsString)
                )
                .andDo(log())
                .andExpect(status().isOk())
                .andReturn();

        // then
        val responseContentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(responseContentAsString).contains(exchangeCurrencyRequest.getReceivingCurrency());
        verify(facade, times(1)).getExchangeRate(any(ExchangeCurrencyRequest.class));
    }

    @Test
    void shouldReturn4XXStatusWhenRequestContainsInvalidData() throws Exception {
        // given
        val exchangeCurrencyRequest = new ExchangeCurrencyRequest("USDAA", null, "EUR", "20220909");
        val requestAsString = objectMapper.writeValueAsString(exchangeCurrencyRequest);

        // when
        when(facade.getExchangeRate(any(ExchangeCurrencyRequest.class)))
                .thenThrow(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Expected reason")
                );

        val mvcResult = mockMvc.perform(
                        post("http://localhost:8080/api/v1/exchange/")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestAsString)
                )
                .andDo(log())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // then
        val responseContentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(responseContentAsString).isEmpty();
    }
}