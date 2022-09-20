package com.test.npb.currency.infrastructure.rest;

import com.test.npb.currency.CurrencyFacade;
import com.test.npb.currency.dto.ExchangeCurrencyRequest;
import com.test.npb.currency.dto.ExchangeCurrencyResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class CurrencyController {

    CurrencyFacade currencyFacade;

    RequestValidator validator;

    @PostMapping(value = "/exchange", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExchangeCurrencyResponse getCurrencyExchangeRate(
            @Valid @RequestBody ExchangeCurrencyRequest request
    ) {
        validator.validate(request);
        return currencyFacade.getExchangeRate(request);
    }
}
