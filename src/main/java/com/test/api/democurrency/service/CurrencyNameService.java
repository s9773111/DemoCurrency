package com.test.api.democurrency.service;

import com.test.api.democurrency.entity.CurrencyName;
import com.test.api.democurrency.webservice.response.BitcoinPriceResponse;

import java.util.List;

public interface CurrencyNameService {

    public CurrencyName addCurrencyItem(String code, String name);

    public List<CurrencyName> getAllCurrencyName();

    public boolean deleteCurrencyNameByCode(String code);
}
