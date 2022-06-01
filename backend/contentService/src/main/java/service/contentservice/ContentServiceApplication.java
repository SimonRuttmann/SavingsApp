package service.contentservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = {"service", "documentDatabaseModule", "relationalDatabaseModule"})
@EnableJpaRepositories("relationalDatabaseModule")
@EntityScan("relationalDatabaseModule")
@SpringBootApplication()
public class ContentServiceApplication implements CommandLineRunner{


    public static void main(String[] args) {
        SpringApplication.run(ContentServiceApplication.class, args);
    }

    @Override
    public void run(String... args){

    }



}
