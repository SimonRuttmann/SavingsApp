package service.inflationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = {"service"})
@EnableScheduling
@SpringBootApplication
public class InflationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InflationServiceApplication.class, args);
    }

}
