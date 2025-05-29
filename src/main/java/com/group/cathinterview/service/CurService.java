package com.group.cathinterview.service;

import com.group.cathinterview.entity.Currency;

import java.util.List;

public interface CurService {
    Currency create(Currency currency);
    Currency update(Long id, Currency currency);
    void delete(Long id);
    Currency getById(Long id);
    List<Currency> getAll();
}
