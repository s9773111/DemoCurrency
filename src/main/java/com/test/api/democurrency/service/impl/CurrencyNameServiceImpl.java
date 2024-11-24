package com.test.api.democurrency.service.impl;

import com.test.api.democurrency.entity.CurrencyName;
import com.test.api.democurrency.repository.CurrencyNameRepository;
import com.test.api.democurrency.service.CurrencyNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CurrencyNameServiceImpl implements CurrencyNameService {

    @Autowired
    private CurrencyNameRepository currencyNameRepository;

    @Override
    public CurrencyName addCurrencyItem(String code, String name) {
        CurrencyName newCurrency = new CurrencyName();
        newCurrency.setCurrencyCode(code);
        newCurrency.setCurrencyName(name);

        newCurrency.setCreateTime(LocalDateTime.now());
        newCurrency.setUpdateTime(LocalDateTime.now());

        return currencyNameRepository.save(newCurrency);
    }
}
