package com.test.api.democurrency.service.impl;

import com.test.api.democurrency.exception.BitcoinPriceException;
import com.test.api.democurrency.service.BitcoinPriceService;
import com.test.api.democurrency.webservice.response.BitcoinPriceResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
}
