package com.test.api.democurrency.controller;

import com.test.api.democurrency.entity.CurrencyName;
import com.test.api.democurrency.repository.CurrencyNameRepository;
import com.test.api.democurrency.service.BitcoinPriceService;
import com.test.api.democurrency.service.CurrencyNameService;
import com.test.api.democurrency.webservice.request.CurrencyRequest;
import com.test.api.democurrency.webservice.response.ApplicationResponse;
import com.test.api.democurrency.webservice.response.BitcoinPriceResponse;
import com.test.api.democurrency.webservice.response.CurrencyAppResponse;
import com.test.api.democurrency.webservice.response.CurrencyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method; // 必須引入 method 方法
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class CurrencyControllerTest {

    @Autowired
    private CurrencyNameService currencyNameService;

    @Autowired
    private CurrencyNameRepository currencyNameRepository; // query

    @Autowired
    private BitcoinPriceService bitcoinPriceService;

    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setup() {
        // 在每次測試之前插入資料
        currencyNameService.addCurrencyItem("USD", "美元");
        currencyNameService.addCurrencyItem("JPY", "日元");

        // init
        restTemplate = new RestTemplate();
        // mockServer = MockRestServiceServer.createServer(restTemplate);
    }


    // 1.測試呼叫查詢幣別對應表資料 API，並顯示其內容
    @Test
    public void testCurrencyNameQuerryAll() {
        List<CurrencyName> list = currencyNameService.getAllCurrencyName();
        System.out.println("資料表內資料筆數："+list.size());

        // transform
        List<CurrencyResponse> list2 = list.stream()
                                           .map(currency -> new CurrencyResponse(currency.getCurrencyCode(), currency.getCurrencyName()))
                                           .collect(Collectors.toList());
        list2.forEach(item -> System.out.println(item.getCode() + "," + item.getName()));
    }

    // 2. 測試呼叫新增幣別對應表資料 API。
    @Test
    public void testCurrencyNameService() {
        // data
        String code = "EUR";
        String name = "歐元";

        // 新增資料
        currencyNameService.addCurrencyItem(code, name);

        // 新增已存在的資料，判斷是否拋出exception
        try {
            currencyNameService.addCurrencyItem(code, name);
        } catch (IllegalArgumentException e) {
            assertEquals(code + "already exists.", e.getMessage());
        }
        // check
//        CurrencyName currency = currencyNameRepository.findByCurrencyCode(code);
//        System.out.println("幣：" + currency);
//        assertNotNull(currency);
//        assertEquals(code, currency.getCurrencyCode());
//        assertEquals(name, currency.getCurrencyName());
    }

    // 3. 測試呼叫更新幣別對應表資料 API，並顯示其內容。
    @Test
    public void testUpdateCurrencyName() {
        CurrencyRequest request = new CurrencyRequest();
        request.setCode("USD");
        request.setName("美金");

        // 檢查資料是否在資料庫內
        CurrencyName existing = currencyNameRepository.findByCurrencyCode("USD");
        if (existing == null) {
            currencyNameRepository.save(currencyNameService.addCurrencyItem(request.getCode(), request.getName()));
        }

        CurrencyName currencyName = currencyNameService.updateCurrencyNameByCode(request);

        assertNotNull(currencyName);
        assertEquals(request.getName(), currencyName.getCurrencyName());
        assertEquals(request.getCode(), currencyName.getCurrencyCode());
    }



    // 4. 測試呼叫刪除幣別對應表資料 API。
    @Test
    public void testDelteCurrencyName() {
        boolean isDeleted = currencyNameService.deleteCurrencyNameByCode("USD");

        if (isDeleted) {
            System.out.println("刪除成功");
        } else {
            System.out.println("查無資料");
        }

        boolean isDeleted2 = currencyNameService.deleteCurrencyNameByCode("EUR");

        if (isDeleted2) {
            System.out.println("刪除成功");
        } else {
            System.out.println("查無資料");
        }
    }


    // 5. 測試呼叫 coindesk API，並顯示其內容。
    @Test
    public void testcoindeskAPI() {

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        // 使用 Mock 模擬測試
        mockServer.expect(requestTo("https://api.coindesk.com/v1/bpi/currentprice.json"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"bpi\": {\"USD\": {\"rate\": \"29,222.9878\"}}}", MediaType.APPLICATION_JSON));

        BitcoinPriceResponse response = restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice.json", BitcoinPriceResponse.class);

        // 驗證
        assertNotNull(response);
        assertEquals("29,222.9878", response.getBpi().getUsd().getRate());

        mockServer.verify();
    }

    // 6. 測試呼叫資料轉換的 API，並顯示其內容。
    @Test
    public void testApplication(){
        ApplicationResponse currency = bitcoinPriceService.getAppliation();

        assertNotNull(currency);
        assertNotNull(currency.getDateTime());
        assertTrue(currency.getCurrencyResponseList().size()>0);

    }
    
}