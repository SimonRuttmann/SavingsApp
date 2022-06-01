package service.userservice;

import com.mongodb.client.MongoClients;
import documentDatabaseModule.model.Category;
import documentDatabaseModule.model.GroupDocument;
import documentDatabaseModule.service.GroupDocumentService;
import dtoAndValidation.dto.content.CategoryDTO;
import dtoAndValidation.dto.content.SavingEntryDTO;
import dtoAndValidation.util.MapperUtil;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * Drops the mongo database and then generates testentries for a fresh database.
 * Class runs through every time it is created and then dies by garbage collector.
 */

@Slf4j
@Service
public class MongoFill {
    private final MongoOperations mongoOps;

    private final GroupDocumentService groupDocumentService;

    private static final int LARGEST_GROUP_ID =10;

    private static final Random random = new Random();

    String[] categoryArray = {"Miete","Lebensmittel","Restaurant","Hund","Katze","Alterversicherung", "Hausversicherung",
            "Rennrad", "BasketballVerein", "Musik Verein", "Zoo","Fastfood","Zocken","Pokern",
            "Glücksspiel","Hausversicherung","Kinder","Kindergarten","Massage","Wellness"};

    ArrayList<Category> categories = new ArrayList<>();

    public MongoFill() throws ParseException {
        log.info("Starting mongo test data routine");

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
        for(int groupId = 1; groupId <= LARGEST_GROUP_ID; groupId++) {
            var d = new GroupDocument();
            d.groupId = (long) groupId;
            GroupDocument savedGroupDoc = groupDocumentService.createDocument(d);

            for (String s : categoryArray) {
                CategoryDTO aCategory = new CategoryDTO(s);
                categories.add(groupDocumentService.insertCategory(savedGroupDoc.groupId, MapperUtil.DTOToCategory(aCategory)));
            }
        }
    }

    private void fillPersonalGroups() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(int month=1 ; month <= 12; month++) {
            for (int day = 1; day <= 28; day++) {
                int repetitionAmount = random.nextInt(49)+1;
                for (int repetitions = 0; repetitions<=repetitionAmount; repetitions++ ) {

                    Long groupId = (long) random.nextInt(1 + LARGEST_GROUP_ID) + 1;
                    int entrynumber = month * 28 + day; // Arbitrary, only used for string naming
                    Double cost = random.nextDouble() * 1000;
                    String name = "Testentry" + entrynumber;
                    Category aCategory = categories.get(random.nextInt(categories.size()));
                    String formattedMonth = dateprefixNumber(month);
                    String formattedDay = dateprefixNumber(day);
                    String formattedHour = dateprefixNumber(random.nextInt(24));
                    String postingUser = "Simon Ruttmann";  //todo Get a random username from groupId

                    SavingEntryDTO savingEntry = new SavingEntryDTO(
                            null, name, cost, MapperUtil.CategoryToDTO(aCategory),
                            formatter.parse("2022-" + formattedMonth + "-" + formattedDay + " " + formattedHour + ":00:00"),
                            postingUser, "Entry number " + entrynumber);

                    groupDocumentService.addSavingEntry(groupId, MapperUtil.DTOToNewSavingEntry(savingEntry));
                }
            }
        }
    }

    private String dateprefixNumber(int number){
        if(number<10) return "0"+number;
        else return String.valueOf(number);
    }
}
