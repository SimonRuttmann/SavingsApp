package service.userservice;

import com.mongodb.client.MongoClients;
import documentDatabaseModule.model.Category;
import documentDatabaseModule.model.GroupDocument;
import documentDatabaseModule.model.SavingEntry;
import documentDatabaseModule.service.GroupDocumentService;
import dtoAndValidation.dto.content.CategoryDTO;
import dtoAndValidation.dto.content.SavingEntryDTO;
import dtoAndValidation.util.MapperUtil;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Drops the mongo database and then generates testentries for a fresh database.
 * Class runs through every time it is created and then dies by garbage collector.
 */
@Service
public class MongoFill {
    private final MongoOperations mongoOps;

    private final GroupDocumentService groupDocumentService;

    public MongoFill() throws ParseException {
        System.out.println("Starting mongo test data routine");

        mongoOps = new MongoTemplate(MongoClients.create(), "test");
        groupDocumentService = new GroupDocumentService(mongoOps);
        recreateCollection();
        addDocuments();
        fillPersonalGroups();
    }

    private void recreateCollection(){
        mongoOps.dropCollection("groupDocuments");
        mongoOps.createCollection("groupDocuments");
    }

    private void addDocuments(){
        for(int groupId =1; groupId <= 10 ; groupId++) {
            var d = new GroupDocument();
            d.groupId = (long) groupId;
            GroupDocument savedGroupDoc = groupDocumentService.createDocument(d);

            // create default Categories
            CategoryDTO miete = new CategoryDTO("Miete");
            CategoryDTO lebensmittel = new CategoryDTO("Lebensmittel");
            CategoryDTO restaurant = new CategoryDTO("Restaurant");

            groupDocumentService.insertCategory(savedGroupDoc.groupId, MapperUtil.DTOToCategory(miete));
            groupDocumentService.insertCategory(savedGroupDoc.groupId, MapperUtil.DTOToCategory(lebensmittel));
            groupDocumentService.insertCategory(savedGroupDoc.groupId, MapperUtil.DTOToCategory(restaurant));
        }
    }

    private void fillPersonalGroups() throws ParseException {
        String[] haustierArray = {"Hund","Katze"};
        String[] versicherungArray = {"Alterversicherung", "Hausversicherung"};
        String[] hobbyArray = {"Rennrad", "BasketballVerein"};
        String[] userArray = {"demo", "annabell", "benni", "chleo", "emil", "franz", "gabriella"};

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        for(int groupId =1; groupId <= 7 ; groupId++) {
            Long id = (long) groupId;

            GroupDocument GroupDoc = groupDocumentService.getGroupDocument(id);

            // create more Categories
            CategoryDTO einHaustier = new CategoryDTO(haustierArray[groupId%2]);
            CategoryDTO eineVersicherung = new CategoryDTO(versicherungArray[groupId%2]);
            CategoryDTO einHobby = new CategoryDTO(hobbyArray[groupId%2]);

            Category haustier = groupDocumentService.insertCategory(GroupDoc.groupId, MapperUtil.DTOToCategory(einHaustier));
            Category versicherung = groupDocumentService.insertCategory(GroupDoc.groupId, MapperUtil.DTOToCategory(eineVersicherung));
            Category hobby = groupDocumentService.insertCategory(GroupDoc.groupId, MapperUtil.DTOToCategory(einHobby));

            //entity Haustier
            SavingEntryDTO anschaffung = new SavingEntryDTO(null,"Anschaffung", 150D, MapperUtil.CategoryToDTO(haustier), (Date) formatter.parse("2022-01-01 15:38:13"), userArray[groupId-1], "Gekauft beim spitzen Züchter" );
            SavingEntryDTO tierArzt1 = new SavingEntryDTO(null,"Tierarztbesuch", 250D, MapperUtil.CategoryToDTO(haustier), (Date) formatter.parse("2022-01-15 15:38:13"), userArray[groupId-1], "Impfung und Flöhebehandlung" );
            SavingEntryDTO tierArzt2 = new SavingEntryDTO(null,"Tierarztbesuch", 350D, MapperUtil.CategoryToDTO(haustier), (Date) formatter.parse("2022-03-16 15:38:13"), userArray[groupId-1], "Zahnstein bei Tieren ist teuer..." );
            SavingEntryDTO tierArzt3 = new SavingEntryDTO(null,"Tierarztbesuch", 50D, MapperUtil.CategoryToDTO(haustier), (Date) formatter.parse("2022-05-31 15:38:13"), userArray[groupId-1], "Zeckenschutz" );
            SavingEntryDTO futter1 = new SavingEntryDTO(null,"Futter und Erstzubehör", 300D, MapperUtil.CategoryToDTO(haustier), (Date) formatter.parse("2021-12-20 15:38:13"), userArray[groupId-1], null );
            SavingEntryDTO futter2 = new SavingEntryDTO(null,"Futter", 60D, MapperUtil.CategoryToDTO(haustier), (Date) formatter.parse("2022-01-16 15:38:13"), userArray[groupId-1], null);
            SavingEntryDTO futter3 = new SavingEntryDTO(null,"Futter und Spielzeug", 75D, MapperUtil.CategoryToDTO(haustier), (Date) formatter.parse("2022-02-01 15:38:13"), userArray[groupId-1], null );
            SavingEntryDTO futter4 = new SavingEntryDTO(null,"Futter", 68.4D, MapperUtil.CategoryToDTO(haustier), (Date) formatter.parse("2022-03-01 15:38:13"), userArray[groupId-1], null );
            SavingEntryDTO futter5 = new SavingEntryDTO(null,"Futter und Trinkbrunnen", 98.80D, MapperUtil.CategoryToDTO(haustier), (Date) formatter.parse("2022-04-01 15:38:13"), userArray[groupId-1], null );
            SavingEntryDTO futter6 = new SavingEntryDTO(null,"Futter", 45.5D, MapperUtil.CategoryToDTO(haustier), (Date) formatter.parse("2022-05-01 15:38:13"), userArray[groupId-1], null );

            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(anschaffung));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(tierArzt1));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(tierArzt2));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(tierArzt3));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(futter1));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(futter2));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(futter3));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(futter4));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(futter5));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(futter6));

            //entity Versicherung
            SavingEntryDTO beraterkosten = new SavingEntryDTO(null,"Beraterkosten", 30D, MapperUtil.CategoryToDTO(versicherung), (Date) formatter.parse("2022-01-18 15:38:13"), userArray[groupId-1], "Beratergebühren bei der SPADA-Bank");
            SavingEntryDTO quartalkosten1 = new SavingEntryDTO(null,"Quartalkosten 2022 - 1", 150D, MapperUtil.CategoryToDTO(versicherung), (Date) formatter.parse("2022-01-23 15:38:13"), userArray[groupId-1], null);
            SavingEntryDTO quartalkosten2 = new SavingEntryDTO(null,"Quartalkosten 2022 - 2", 150D, MapperUtil.CategoryToDTO(versicherung), (Date) formatter.parse("2022-04-01 15:38:13"), userArray[groupId-1], null);

            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(beraterkosten));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(quartalkosten1));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(quartalkosten2));

            // entity Hobby
            SavingEntryDTO vereinzahlung1 = new SavingEntryDTO(null,"Vereinzahlung Jan", 65D, MapperUtil.CategoryToDTO(hobby), (Date) formatter.parse("2022-01-15 15:38:13"), userArray[groupId-1], null);
            SavingEntryDTO vereinzahlung2 = new SavingEntryDTO(null,"Vereinzahlung Feb", 65D, MapperUtil.CategoryToDTO(hobby), (Date) formatter.parse("2022-02-15 15:38:13"), userArray[groupId-1], null);
            SavingEntryDTO vereinzahlung3 = new SavingEntryDTO(null,"Vereinzahlung Mär", 65D, MapperUtil.CategoryToDTO(hobby), (Date) formatter.parse("2022-03-15 15:38:13"), userArray[groupId-1], null);
            SavingEntryDTO vereinzahlung4 = new SavingEntryDTO(null,"Vereinzahlung Apr", 65D, MapperUtil.CategoryToDTO(hobby), (Date) formatter.parse("2022-04-15 15:38:13"), userArray[groupId-1], null);
            SavingEntryDTO vereinzahlung5 = new SavingEntryDTO(null,"Vereinzahlung Mai", 65D, MapperUtil.CategoryToDTO(hobby), (Date) formatter.parse("2021-05-15 15:38:13"), userArray[groupId-1], null);

            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(vereinzahlung1));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(vereinzahlung2));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(vereinzahlung3));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(vereinzahlung4));
            groupDocumentService.addSavingEntry(id, MapperUtil.DTOToNewSavingEntry(vereinzahlung5));
        }
    }
}
