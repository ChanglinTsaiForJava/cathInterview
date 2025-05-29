package com.group.cathinterview.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvertedResponse {
    private String updatedTime;
    private List<CurrencyItem> currencyList;
}
