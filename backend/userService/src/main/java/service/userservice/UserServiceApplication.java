package service.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"documentDatabaseModule", "relationalDatabaseModule","service"})
@EnableJpaRepositories("relationalDatabaseModule")
@EntityScan("relationalDatabaseModule")
@SpringBootApplication()
public class UserServiceApplication implements CommandLineRunner {

    @Autowired
    private MongoTestDataCreator mongoTestDataCreator;

    private static final String MigrateTestData = "migrate";

    public static void main(String[] args)  {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {

        if(MigrateTestData.equals(System.getenv("test"))) {
            mongoTestDataCreator.createTestData();
        }

    }


}
