package com.group.cathinterview.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CoindeskServiceImpl implements CoindeskService {

    private final String COINDESK_API_URL = "https://kengp3.github.io/blog/coindesk.json";

//    @Override
//    public JSONObject getRawData() {
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject(COINDESK_API_URL, String.class);
//        return new JSONObject(response); // 將字串轉成 JSON 回傳
//    }
@Override
public Map<String, Object> getRawData() {
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject("https://kengp3.github.io/blog/coindesk.json", Map.class);
}
}
