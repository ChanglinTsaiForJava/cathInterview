package com.group.cathinterview.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyItem {
    private String code;
    private String name;
    private Double rate;
}
