package com.test.api.democurrency.service.impl;

import com.test.api.democurrency.entity.CurrencyName;
import com.test.api.democurrency.repository.CurrencyNameRepository;
import com.test.api.democurrency.service.CurrencyNameService;
import com.test.api.democurrency.webservice.request.CurrencyRequest;
import com.test.api.democurrency.webservice.response.CurrencyResponse;
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

    @Override
    public CurrencyName updateCurrencyNameByCode(CurrencyRequest request) {
        // 檢查是否有資料，代碼不變
        CurrencyName currencyNamebyCode = currencyNameRepository.findByCurrencyCode(request.getCode());
        // 檢查是否有資料，名稱不變
        CurrencyName currencyNamebyName = currencyNameRepository.findByCurrencyName(request.getName());

        if (currencyNamebyCode == null) {
            // 若Code改變時
            if (currencyNamebyName == null) {
                // Code與Name無變
                throw new IllegalArgumentException("Data not exists.");
            } else {
                currencyNamebyName.setCurrencyCode(request.getCode());
                currencyNamebyName.setUpdateTime(LocalDateTime.now());
                return currencyNameRepository.save(currencyNamebyName);
            }
        } else {
            // 若Name改變時
            currencyNamebyCode.setCurrencyName(request.getName());
            currencyNamebyCode.setUpdateTime(LocalDateTime.now());
            return currencyNameRepository.save(currencyNamebyCode);
        }

    }
}
