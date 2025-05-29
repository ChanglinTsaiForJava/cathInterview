package com.group.cathinterview.service;


import com.group.cathinterview.DTO.ConvertedResponse;

import java.util.Map;

public interface CoindeskService {

    Map<String, Object> getRawData();

    ConvertedResponse getConvertedData();
}
