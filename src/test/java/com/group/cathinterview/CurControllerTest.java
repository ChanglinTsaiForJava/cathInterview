package com.group.cathinterview;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.cathinterview.entity.Currency;
import com.group.cathinterview.repo.CurRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurControllerTest {


    private final MockMvc mockMvc;
    private final CurRepo curRepo;
    private final ObjectMapper objectMapper;

    @Autowired
    public CurControllerTest(MockMvc mockMvc, CurRepo curRepo, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.curRepo = curRepo;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void setup() {
        // 每次測試前清空資料表
        curRepo.deleteAll();
    }

    @Test
    public void testCreateCurrency() throws Exception {
        Currency usd = new Currency(null, "USD", "美元");

        mockMvc.perform(post("/api/currency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("USD")))
                .andExpect(jsonPath("$.mandarinName", is("美元")));
    }

    @Test
    public void testGetAllCurrencies() throws Exception {
        curRepo.saveAll(Arrays.asList(
                new Currency(null, "USD", "美元"),
                new Currency(null, "EUR", "歐元")
        ));

        mockMvc.perform(get("/api/currency"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetById() throws Exception {
        Currency saved = curRepo.save(new Currency(null, "GBP", "英鎊"));

        mockMvc.perform(get("/api/currency/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("GBP")));
    }

    @Test
    public void testUpdateCurrency() throws Exception {
        Currency saved = curRepo.save(new Currency(null, "JPY", "日圓"));

        saved.setMandarinName("日本圓");
        mockMvc.perform(put("/api/currency/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mandarinName", is("日本圓")));
    }

    @Test
    public void testDeleteCurrency() throws Exception {
        Currency saved = curRepo.save(new Currency(null, "TWD", "新台幣"));

        mockMvc.perform(delete("/api/currency/" + saved.getId()))
                .andExpect(status().isOk());

        assertFalse(curRepo.findById(saved.getId()).isPresent());
    }
}
