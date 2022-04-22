package service.inflationservice;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DataJobs {
    private String data = null;

    //Scheduled to run once on call and then every 10 days after server startup at 0:00
    @Scheduled(cron = "0 0 0 1/10 * ?")
    private void getApiData(){
        System.out.println("Scheduled job is running");
        /// TODO: 18.04.2022 Replace placeholder uri with real one
        // Query to calculate inflation price (with real values)
        // https://www.statbureau.org/calculate-inflation-price-jsonp?jsoncallback=jQuery1112038048534897455344_1650559210757&country=germany&start=2012%2F1%2F1&end=2012%2F12%2F1&amount=100&format=true&_=1650559210763

        String uri = "https://www.statbureau.org/get-data-json?jsoncallback=jQuery1112038048534897455344_1650559210757&country=germany";
        RestTemplate restTemplate = new RestTemplate();

        data = restTemplate.getForObject(uri,String.class);
    }

    public String getData() {
        if(data == null) getApiData();
        return data ;
    }
}
