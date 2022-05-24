package service.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"service", "documentDatabaseModule", "relationalDatabaseModule"})
@EnableJpaRepositories("relationalDatabaseModule.service")
@EntityScan("relationalDatabaseModule.model")
@SpringBootApplication()
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
