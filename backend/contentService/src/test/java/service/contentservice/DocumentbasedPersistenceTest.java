package service.contentservice;


import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import service.contentservice.persistence.IGroupDocumentService;
import service.contentservice.persistence.documentbased.Category;
import service.contentservice.persistence.documentbased.GroupDocument;
import service.contentservice.persistence.documentbased.IGroupDocumentRepository;
import service.contentservice.persistence.documentbased.SavingEntry;
import org.junit.jupiter.api.Test;


import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class DocumentbasedPersistenceTest {

    @Autowired
    public IGroupDocumentRepository groupDocumentRepository;

    @Autowired
    public IGroupDocumentService groupDocumentService;

    @Test
    public void persistenceTest() throws InterruptedException {

        Assertions.assertNotNull(groupDocumentService);

        groupDocumentRepository.deleteAll();
        Assertions.assertNotNull(groupDocumentRepository);

        Category categoryPet = new Category("pet");
        Category categoryEat = new Category("eat");
        Category categoryWash = new Category("wash");

        SavingEntry savingEntry1 = new SavingEntry();
        savingEntry1.creator = "Hans";
        savingEntry1.name="Donuts";
        savingEntry1.costBalance= -1000.51;
        savingEntry1.creationDate = new Date();
        savingEntry1.description = "My cat is eating too much!";
        savingEntry1.category = categoryPet;

        SavingEntry savingEntry2 = new SavingEntry();
        savingEntry2.creator = "Hans";
        savingEntry2.name="Donuts";
        savingEntry2.costBalance= -1.52;
        savingEntry2.creationDate = new Date();
        savingEntry2.description = "I am eating too less!";
        savingEntry2.category = categoryWash;

        GroupDocument groupDocument = new GroupDocument();
        groupDocument.groupId = 1L;
        groupDocument.savingEntries.addAll(Arrays.asList(savingEntry1, savingEntry2));
        groupDocument.categories.addAll(Arrays.asList(categoryEat, categoryPet, categoryWash));

        GroupDocument savedGroupDocument = groupDocumentRepository.save(groupDocument);
        Assertions.assertNotNull(savedGroupDocument);
        Assertions.assertNotNull(savedGroupDocument.getId());

        Optional<GroupDocument> queriedDocument = groupDocumentRepository.findById(savedGroupDocument.getId());
        Assertions.assertTrue(queriedDocument.isPresent());

        Assertions.assertEquals(savedGroupDocument, queriedDocument.get());
        Assertions.assertEquals(2, queriedDocument.get().savingEntries.size());


    }
}
