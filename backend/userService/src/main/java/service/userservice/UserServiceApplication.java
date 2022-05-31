package service.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.text.ParseException;

@ComponentScan(basePackages = {"documentDatabaseModule", "relationalDatabaseModule","service"})
@EnableJpaRepositories("relationalDatabaseModule")
@EntityScan("relationalDatabaseModule")
@SpringBootApplication()
public class UserServiceApplication {

    public static void main(String[] args) throws ParseException {

        SpringApplication.run(UserServiceApplication.class, args);
        if(Boolean.parseBoolean(System.getenv("test")))
        {
            new MongoFill();
        }
    }

}
