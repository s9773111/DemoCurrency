package com.test.api.democurrency.controller;

import com.test.api.democurrency.entity.CurrencyName;
import com.test.api.democurrency.repository.CurrencyNameRepository;
import com.test.api.democurrency.service.CurrencyNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class CurrencyControllerTest {

    @Autowired
    private CurrencyNameService currencyNameService;

    @Autowired
    private CurrencyNameRepository currencyNameRepository; // query


    // Test Add
    @Test
    public void testCurrencyNameService() {
        String code = "USD";
        String name = "美元";
        // add
        currencyNameService.addCurrencyItem(code, name);

        // check
        CurrencyName currency = currencyNameRepository.findByCurrencyCode(code);
        System.out.println("幣：" + currency);
        assertNotNull(currency);
        assertEquals(code, currency.getCurrencyCode());
        assertEquals(name, currency.getCurrencyName());
    }
}
