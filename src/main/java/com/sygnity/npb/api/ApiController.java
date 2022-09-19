package com.sygnity.npb.api;

import com.sygnity.npb.service.CurrencyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class ApiController {

    CurrencyService currencyService;

    @PostMapping("/exchange")
    public String getCurrencyExchangeRate(
            @Valid @RequestBody ExchangeCurrencyRequest request
    ) {
        ExchangeRateRequestValidator.validate(request);
        return currencyService.getExchangeRate(ExchangeRateRequestMapper.map(request));
    }
}
