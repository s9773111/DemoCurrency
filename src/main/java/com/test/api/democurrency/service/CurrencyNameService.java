package com.test.api.democurrency.service;

import com.test.api.democurrency.entity.CurrencyName;
import com.test.api.democurrency.webservice.response.BitcoinPriceResponse;

public interface CurrencyNameService {

    public CurrencyName addCurrencyItem(String code, String name);

}
