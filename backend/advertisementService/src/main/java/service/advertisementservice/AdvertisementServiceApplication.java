package service.advertisementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "inMemoryDatabaseService.service")
@SpringBootApplication
public class AdvertisementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvertisementServiceApplication.class, args);
    }

}
