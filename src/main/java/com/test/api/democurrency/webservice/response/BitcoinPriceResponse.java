package com.test.api.democurrency.webservice.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Currency;

@Getter
@Setter
public class BitcoinPriceResponse {
    private Time time;
    private String disclaimer;
    private Bpi bpi;

    public static class Time {
        @JsonProperty("updated")
        private String updated;

        @JsonProperty("updatedISO")
        private String updatedISO;

        @JsonProperty("updateduk")
        private String updateduk;
    }

    @Getter
    public static class Bpi {

        @JsonProperty("USD")
        private Currency usd;

        @JsonProperty("GBP")
        private Currency gbp;

        @JsonProperty("EUR")
        private Currency eur;
    }

    @Getter
    public static class Currency {
        @JsonProperty("code")
        private String code;

        @JsonProperty("symbol")
        private String symbol;

        @JsonProperty("rate")
        private String rate;

        @JsonProperty("description")
        private String description;

        @JsonProperty("rate_float")
        private float rateFloat;
    }
}
