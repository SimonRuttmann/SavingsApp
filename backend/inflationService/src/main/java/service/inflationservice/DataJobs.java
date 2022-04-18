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
        String uri = "http://localhost:8010/global";
        RestTemplate restTemplate = new RestTemplate();

        data = restTemplate.getForObject(uri,String.class);
    }

    public String getData() {
        if(data == null) getApiData();
        return data ;
    }
}
