package com.test.api.democurrency.webservice.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CurrencyAppResponse {
    private String code;

    private String name;

    private String rate;

    public CurrencyAppResponse(String code, String name, String rate) {
        this.code = code;
        this.name = name;
        this.rate = rate;
    }
}
