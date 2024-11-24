package com.test.api.democurrency.webservice.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyResponse {
    private String code;

    private String name;

    public CurrencyResponse(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
