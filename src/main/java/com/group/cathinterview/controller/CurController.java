package com.group.cathinterview.controller;
import com.group.cathinterview.entity.Currency;
import com.group.cathinterview.service.CurService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


//CRUD API
@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
public class CurController {

    private final CurService curService;
    //找單筆跟全部
    @GetMapping("/{id}")
    public Currency getById(@PathVariable Long id) {
        return curService.getById(id);
    }

    @GetMapping
    public List<Currency> getAll() {
        return curService.getAll();
    }

    @PostMapping
    public Currency create(@RequestBody Currency curr) {
        return curService.create(curr);
    }

    @PutMapping("/{id}")
    public Currency update(@PathVariable Long id, @RequestBody Currency currency) {
        return curService.update(id, currency);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        curService.delete(id);
    }

}
