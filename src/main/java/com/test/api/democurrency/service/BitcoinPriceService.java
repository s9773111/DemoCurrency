package com.test.api.democurrency.service;

import com.test.api.democurrency.webservice.response.BitcoinPriceResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

public interface BitcoinPriceService {
    public  BitcoinPriceResponse getCoindest();
}
