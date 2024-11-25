package com.test.api.democurrency.webservice.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApplicationResponse {

    private String dateTime;

    private List<CurrencyAppResponse> currencyResponseList;

    public ApplicationResponse(String dateTime, List<CurrencyAppResponse> currencyResponseList) {
        this.dateTime = dateTime;
        this.currencyResponseList = currencyResponseList;
    }

    public ApplicationResponse() {
        this.dateTime = null;
        this.currencyResponseList = new ArrayList<>();
    }
}
