package com.group.cathinterview;

import com.group.cathinterview.DTO.ConvertedResponse;
import com.group.cathinterview.DTO.CurrencyItem;
import com.group.cathinterview.entity.Currency;
import com.group.cathinterview.repo.CurRepo;
import com.group.cathinterview.service.CoindeskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CoindeskServiceTest {

    private CurRepo curRepo;
    private CoindeskServiceImpl service;

    @BeforeEach
    public void setup() {
        curRepo = mock(CurRepo.class);
        service = new CoindeskServiceImpl(curRepo);
    }

    @Test
    public void testConvertedDataNotNull() {
        mockCurrencies();
        ConvertedResponse result = service.getConvertedData();
        assertNotNull(result);
        // print out the result as the guidance indicates
        System.out.println("result of converted data test：" + result);
    }

    @Test
    public void testUpdatedTimeFormatWithFormatter() {
        mockCurrencies();
        ConvertedResponse result = service.getConvertedData();
        String timeStr = result.getUpdatedTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        assertDoesNotThrow(() -> {
            LocalDateTime parsedTime = LocalDateTime.parse(timeStr, formatter);
            System.out.println("Time" + parsedTime);
        }, "failed to parse time");
    }

    @Test
    public void testCurrencyListSize() {
        mockCurrencies();
        ConvertedResponse result = service.getConvertedData();
        assertEquals(3, result.getCurrencyList().size());
        System.out.println("***********  List of currencies：" + result.getCurrencyList().size()+" ***********");

    }

    @Test
    public void testUsdCurrencyName() {
        mockCurrencies();
        ConvertedResponse result = service.getConvertedData();
        CurrencyItem usd = result.getCurrencyList().stream()
                .filter(c -> c.getCode().equals("USD"))
                .findFirst()
                .orElse(null);

        assertNotNull(usd);
        assertEquals("美元", usd.getName());
        System.out.println("USD 幣別名稱：" + usd.getName());

    }

    @Test
    public void testCurrencyRateIsPositive() {
        mockCurrencies();
        ConvertedResponse result = service.getConvertedData();
        result.getCurrencyList().forEach(item -> {
            assertTrue(item.getRate() > 0, item.getCode() + " 匯率不是正數");
            System.out.println(item.getCode() + " 匯率：" + item.getRate());
        });
    }

    private void mockCurrencies() {
        when(curRepo.findByCode("USD")).thenReturn(new Currency(null, "USD", "美元"));
        when(curRepo.findByCode("GBP")).thenReturn(new Currency(null, "GBP", "英鎊"));
        when(curRepo.findByCode("EUR")).thenReturn(new Currency(null, "EUR", "歐元"));
    }
}
