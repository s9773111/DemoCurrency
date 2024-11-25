package com.test.api.democurrency.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.api.democurrency.exception.BitcoinPriceException;
import com.test.api.democurrency.service.BitcoinPriceService;
import com.test.api.democurrency.webservice.response.ApplicationResponse;
import com.test.api.democurrency.webservice.response.BitcoinPriceResponse;
import com.test.api.democurrency.webservice.response.CurrencyAppResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Service
public class BitcoinPriceServiceImpl implements BitcoinPriceService {

    @Override
    public BitcoinPriceResponse getCoindest() {
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        RestTemplate restTemplate = new RestTemplate();

        try {
            BitcoinPriceResponse response = restTemplate.getForObject(url, BitcoinPriceResponse.class);
            return response;
        } catch (RestClientException e) {
            throw new BitcoinPriceException("Failed to fetch Bitcoin price from API", e);
        }
    }

//    @Override
//    public ApplicationResponse getAppliation() {
//        // Method1 : obtain data using object
//        BitcoinPriceResponse bitcoinPrice = getCoindest();
//
//        // datetime  Nov 25, 2024 14:42:33 UTC
//        String updatedTime = bitcoinPrice.getTime().getUpdated();
//        // step1 解析原始格式
//        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss z");
//        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("MMM dd, yyyy HH:mm:ss z").toFormatter(Locale.ENGLISH);
//        ZonedDateTime zonedDateTime = ZonedDateTime.parse(updatedTime, formatter);
//        System.out.println("zonedDateTime:" + zonedDateTime);
//        // step2 格式化
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        String formattedDate = zonedDateTime.format(outputFormatter);
//
//        // currency
//        List<CurrencyAppResponse> currencyResponseList = new ArrayList<>();
//        BitcoinPriceResponse.Bpi bpi = bitcoinPrice.getBpi();
//        currencyResponseList.add(convertCurrency(bpi.getUsd(), "美元"));
//        currencyResponseList.add(convertCurrency(bpi.getGbp(), "英鎊"));
//        currencyResponseList.add(convertCurrency(bpi.getEur(), "歐元"));
//
//
//        return new ApplicationResponse(formattedDate, currencyResponseList);
//    }

    @Override
    public ApplicationResponse getAppliation() {
        // Method2 : obtain data using json
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        try {
            // 取得 json字串
            String jsonResponse = restTemplate.getForObject(url, String.class);

            // 解析 json字串
            JsonNode rootNode = mapper.readTree(jsonResponse);

            // 1
            // datetime  Nov 25, 2024 14:42:33 UTC
            String updatedTime = rootNode.path("time").path("updated").asText();
            // step1 解析原始格式
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss z");
            DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("MMM dd, yyyy HH:mm:ss z").toFormatter(Locale.ENGLISH);
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(updatedTime, formatter);
            // step2 格式化
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String formattedDate = zonedDateTime.format(outputFormatter);

            // 2
            List<CurrencyAppResponse> currencyAppResponseList = new ArrayList<>();
            JsonNode bpiNode = rootNode.path("bpi");

            Iterator<JsonNode> currencyNodes = bpiNode.elements();
            while (currencyNodes.hasNext()) {
                JsonNode currencyNode = currencyNodes.next();
                String code = currencyNode.path("code").asText();
                String name;
                String rate = currencyNode.path("rate").asText();
                switch (code) {
                    case "USD":
                        name = "美元";
                        break;
                    case "GBP":
                        name = "英鎊";
                        break;
                    case "EUR":
                        name = "歐元";
                        break;
                    default:
                        name = "錢";  // 若沒有匹配的情況，可以直接使用 description 或設為 "未知"
                        break;
                }

                CurrencyAppResponse currency = new CurrencyAppResponse(code, name, rate);
                currencyAppResponseList.add(currency);
            }
            return new ApplicationResponse(formattedDate, currencyAppResponseList);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch Bitcoin price from API", e);
        }

    }


    private CurrencyAppResponse convertCurrency(BitcoinPriceResponse.Currency currency, String name) {
        return new CurrencyAppResponse(currency.getCode(), name, currency.getRate());
    }
}
