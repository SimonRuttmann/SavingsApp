package service.userservice;

import documentDatabaseModule.model.SavingEntry;
import documentDatabaseModule.service.GroupDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import documentDatabaseModule.model.Category;
import documentDatabaseModule.model.GroupDocument;
import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Drops the mongo database and then generates testentries for a fresh database.
 * Class runs through every time it is created and then dies by garbage collector.
 */

@Slf4j
@Service
public class MongoTestDataCreator {


    private final GroupDocumentService groupDocumentService;

    private static final int LARGEST_GROUP_ID =10;

    private static final Random random = new Random();

    String[] categoryArray = {"Miete","Lebensmittel","Restaurant","Hund","Katze","Alterversicherung", "Hausversicherung",
                              "Rennrad", "BasketballVerein", "Musik Verein", "Zoo","Fastfood","Zocken","Pokern",
                              "Glücksspiel","Hausversicherung","Kinder","Kindergarten","Massage","Wellness"};

    ArrayList<Category> categories = new ArrayList<>();

    @Autowired
    public MongoTestDataCreator(GroupDocumentService groupDocumentService) throws ParseException {
        log.info("Starting mongo test data routine");

        this.groupDocumentService = groupDocumentService;
    }

    public void createTestData(){
        addDocuments();
        fillPersonalGroups();
    }

    private void addDocuments(){
        for(int groupId = 1; groupId <= LARGEST_GROUP_ID; groupId++) {
            var d = new GroupDocument();
            d.groupId = (long) groupId;
            GroupDocument savedGroupDoc = groupDocumentService.createDocument(d);

            addCategories(savedGroupDoc);
        }
    }

    private void addCategories(GroupDocument savedGroupDoc){
        for (String s : categoryArray) {
            Category aCategory = new Category(s);
            categories.add(groupDocumentService.insertCategory(savedGroupDoc.groupId, aCategory));
        }
    }

    private void fillPersonalGroups(){
        for(int month=1 ; month <= 12; month++) {
            for (int day = 1; day <= 28; day++) {

                createSavingEntry(month, day);
            }
        }
    }

    private void createSavingEntry(int month, int day){

        int repetitionAmount = random.nextInt(49)+1;
        for (int repetitions = 0; repetitions<=repetitionAmount; repetitions++ ) {

            Long groupId = (long) random.nextInt(1 + LARGEST_GROUP_ID) + 1;

            int entryNumber = month * 28 + day;
            String name = "Testentry" + entryNumber;
            String description =  "Entry number " + entryNumber;

            Category category = categories.get(random.nextInt(categories.size()));
            Date creationDate = createRandomizedDate(day, month, random.nextInt(24));

            String postingUser = setPostingUser(groupId);
            Double cost = random.nextDouble() + random.nextInt(1000 + 1000) - 1000;

            SavingEntry savingEntry = new SavingEntry(name, cost, category, postingUser, creationDate, description);
            groupDocumentService.addSavingEntry(groupId, savingEntry);
        }

    }

    private String setPostingUser(Long groupId){
        String[] group8 = {"demo", "annabell", "benni", "chleo"};
        String[] group9 = {"demo", "emil", "franz", "gabriella" };
        String[] group10 = {"annabell", "benni", "chleo", "emil", "franz", "gabriella"};

        return switch (groupId.intValue()) {
            case 1 -> "demo";
            case 2 -> "annabell";
            case 3 -> "benni";
            case 4 -> "chleo";
            case 5 -> "emil";
            case 6 -> "franz";
            case 7 -> "gabriella";
            case 8 -> group8[random.nextInt(4)];
            case 9 -> group9[random.nextInt(4)];
            case 10 -> group10[random.nextInt(6)];
            default -> "defautlUser";
        };
    }

    private String createDatePrefixNumber(int number){
        if(number<10) return "0"+number;
        else return String.valueOf(number);
    }

    private Date createRandomizedDate(int day, int month, int datePrefix){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String formattedMonth = createDatePrefixNumber(month);
        String formattedDay = createDatePrefixNumber(day);
        String formattedHour = createDatePrefixNumber(datePrefix);
        Date creationDate;

        try {
            creationDate = formatter.parse("2022-" + formattedMonth + "-" + formattedDay + " " + formattedHour + ":00:00");
        } catch (ParseException e) {
            creationDate = new Date();
        }

        return creationDate;
    }

}
