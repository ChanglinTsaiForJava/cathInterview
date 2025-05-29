package com.group.cathinterview.service;

import com.group.cathinterview.entity.Currency;
import com.group.cathinterview.repo.CurRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service


public class CurServiceImpl implements CurService {
    private final CurRepo repository;

    public CurServiceImpl(CurRepo repository) {
        this.repository = repository;
    }
    //Create currency
    @Override
    public Currency create(Currency currency) {
        return repository.save(currency);
    }

    //update currency, use id to locate it, set the code and mandarin name
    @Override
    public Currency update(Long id, Currency currency) {
        Currency curr = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));

        curr.setCode(currency.getCode());
        curr.setMandarinName(currency.getMandarinName());

        return repository.save(curr);
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Currency getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));
    }

    @Override
    public List<Currency> getAll() {
        return repository.findAll();
    }
}
