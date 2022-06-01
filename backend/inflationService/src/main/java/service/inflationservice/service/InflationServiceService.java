package service.inflationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import service.inflationservice.model.InflationDto;

import java.net.http.HttpResponse;

@Slf4j
@Component
public class InflationServiceService {
    private String data = null;

    //Scheduled to run once on call and then every 10 days after server startup at 0:00
    @Scheduled(cron = "0 0 0 1/10 * ?")
    private void getApiData(){
        log.info("Scheduled job is running");
        // Query to calculate inflation price (with real values)
        // https://www.statbureau.org/calculate-inflation-price-jsonp?jsoncallback=jQuery1112038048534897455344_1650559210757&country=germany&start=2012%2F1%2F1&end=2012%2F12%2F1&amount=100&format=true&_=1650559210763

        String uri = "https://www.statbureau.org/get-data-json?jsoncallback=jQuery1112038048534897455344_1650559210757&country=germany";
        RestTemplate restTemplate = new RestTemplate();

        data = restTemplate.getForObject(uri,String.class);
    }

    public ResponseEntity<InflationDto> getLatestInflationData() throws ParseException {
        JSONParser parser = new JSONParser();

        Object obj = null;

        try {
            obj = parser.parse(getAllInflationData());
            JSONArray json = (JSONArray) obj;

            obj= parser.parse(json.get(0).toString());
            JSONObject jsonObject = (JSONObject) obj;
            var dto = new InflationDto();
            dto.setInflationValueInPercent( Double.parseDouble(jsonObject.get("InflationRateRounded").toString()));

            return new ResponseEntity<>(dto, HttpStatus.OK) ;
        }catch (Exception e){
            log.error("Failed to parse json from api. Exception: "+e.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    private String getAllInflationData() {
        if(data == null) getApiData();
        return data ;
    }
}
