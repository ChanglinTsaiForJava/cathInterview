package com.group.cathinterview;

import com.group.cathinterview.DTO.ConvertedResponse;
import com.group.cathinterview.DTO.CurrencyItem;
import com.group.cathinterview.controller.CoindeskController;
import com.group.cathinterview.entity.Currency;
import com.group.cathinterview.repo.CurRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CoindeskControllerTest {

    private final CoindeskController coindeskController;
    private final CurRepo curRepo;

    @Autowired
    public CoindeskControllerTest(CoindeskController coindeskController, CurRepo curRepo) {
        this.coindeskController = coindeskController;
        this.curRepo = curRepo;
    }

    @BeforeEach
    public void setup() {
        curRepo.deleteAll();
        curRepo.saveAll(Arrays.asList(
                new Currency(null, "USD", "美元"),
                new Currency(null, "GBP", "英鎊"),
                new Currency(null, "EUR", "歐元")
        ));
    }

    @Test
    public void testConvertedData() {
        ConvertedResponse response = coindeskController.getConverted();

        assertNotNull(response);
        assertNotNull(response.getUpdatedTime());
        assertEquals(3, response.getCurrencyList().size());

        CurrencyItem usd = response.getCurrencyList().stream()
                .filter(c -> c.getCode().equals("USD"))
                .findFirst()
                .orElse(null);

        assertNotNull(usd);
        assertEquals("美元", usd.getName());
        assertTrue(usd.getRate() > 0);
    }
}