package com.test.api.democurrency.service.impl;

import com.test.api.democurrency.entity.CurrencyName;
import com.test.api.democurrency.repository.CurrencyNameRepository;
import com.test.api.democurrency.service.CurrencyNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CurrencyNameServiceImpl implements CurrencyNameService {

    @Autowired
    private CurrencyNameRepository currencyNameRepository;

    @Override
    public CurrencyName addCurrencyItem(String code, String name) {

        // check 是否存在
        CurrencyName existing = currencyNameRepository.findByCurrencyCode(code);

        if (existing != null) {
            throw new IllegalArgumentException(code + "already exists.");
        }

        CurrencyName newCurrency = new CurrencyName();
        newCurrency.setCurrencyCode(code);
        newCurrency.setCurrencyName(name);

        newCurrency.setCreateTime(LocalDateTime.now());
        newCurrency.setUpdateTime(LocalDateTime.now());

        return currencyNameRepository.save(newCurrency);
    }

    @Override
    public List<CurrencyName> getAllCurrencyName() {
        return currencyNameRepository.findAll();
    }

    @Override
    public boolean deleteCurrencyNameByCode(String code) {
        CurrencyName currencyName = currencyNameRepository.findByCurrencyCode(code);
        if (currencyName != null) {
            currencyNameRepository.delete(currencyName);
            return true;
        }
        return false;
    }
}
