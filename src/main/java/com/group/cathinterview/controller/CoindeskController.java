package com.group.cathinterview.controller;

import com.group.cathinterview.DTO.ConvertedResponse;
import com.group.cathinterview.service.CoindeskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/coindesk")
@RequiredArgsConstructor
public class CoindeskController {

    private final CoindeskService coindeskService;

    @GetMapping("/raw")
    public Map<String, Object> getRawData() {
        return coindeskService.getRawData();
    }

    @GetMapping("/converted")
    public ConvertedResponse getConverted() {
        return coindeskService.getConvertedData();
    }

}