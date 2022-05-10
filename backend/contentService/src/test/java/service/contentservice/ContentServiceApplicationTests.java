package service.contentservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import service.contentservice.persistence.relational.repository.PersonRepository;

@SpringBootTest
class ContentServiceApplicationTests {

    @Autowired
    PersonRepository personRepository;
    @Test
    void contextLoads() {
        Assert.notNull(personRepository);
    }

}
