package service.contentservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.contentservice.entity2.DocumentEntity;
import service.contentservice.entity2.IDocumentRepository;

@SpringBootApplication
public class ContentServiceApplication implements CommandLineRunner{

    @Autowired
    private IDocumentRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(ContentServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repository.deleteAll();

        // save a couple of customers
        repository.save(new DocumentEntity("Alice", "Smith"));
        repository.save(new DocumentEntity("Bob", "Smith"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (DocumentEntity customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (DocumentEntity customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }

    }

}
