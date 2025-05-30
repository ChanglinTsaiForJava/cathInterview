package com.group.cathinterview.service;
import com.group.cathinterview.DTO.ConvertedResponse;
import com.group.cathinterview.DTO.CurrencyItem;
import com.group.cathinterview.entity.Currency;
import com.group.cathinterview.repo.CurRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CoindeskServiceImpl implements CoindeskService {
    private final CurRepo curRepo;
    private final String COINDESK_API_URL = "https://kengp3.github.io/blog/coindesk.json";

    private String formatIsoTime(String isoTime) {
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = isoFormat.parse(isoTime);
            return targetFormat.format(date);
        } catch (Exception e) {
            return "Not able to parse time";
        }
    }
    @Override
    public Map<String, Object> getRawData() {
        RestTemplate restTemplate = new RestTemplate();
        // we send out a get method and convert the json response into a map<str, obj>
        return restTemplate.getForObject("https://kengp3.github.io/blog/coindesk.json", Map.class);
    }

    @Override
    public ConvertedResponse getConvertedData() {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> json = restTemplate.getForObject("https://kengp3.github.io/blog/coindesk.json", Map.class);


        Map<String, Object> timeMap = (Map<String, Object>) json.get("time");
        String isoTime = (String) timeMap.get("updatedISO");

        String formattedTime = formatIsoTime(isoTime);


        Map<String, Object> bpiMap = (Map<String, Object>) json.get("bpi");

        List<CurrencyItem> resultList = new ArrayList<>();

        for (String code : bpiMap.keySet()) {
            Map<String, Object> currencyData = (Map<String, Object>) bpiMap.get(code);
            Double rate = Double.valueOf(currencyData.get("rate_float").toString());


            Currency currencyEntity = curRepo.findByCode(code);
            String chineseName = (currencyEntity != null) ? currencyEntity.getMandarinName() : "未知幣別";

            resultList.add(new CurrencyItem(code, chineseName, rate));
        }


        return new ConvertedResponse(formattedTime, resultList);
    }
}
