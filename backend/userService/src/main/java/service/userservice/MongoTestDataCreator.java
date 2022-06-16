package service.userservice;

import documentDatabaseModule.model.SavingEntry;
import documentDatabaseModule.service.GroupDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

import documentDatabaseModule.model.Category;
import documentDatabaseModule.model.GroupDocument;
import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Drops the mongo database and then generates testentries for a fresh database.
 * Class runs through every time it is created and then dies by garbage collector.
 */

@Slf4j
@Service
public class MongoTestDataCreator {

    private final MongoOperations mongoOps;

    private final GroupDocumentService groupDocumentService;

    private static final int LARGEST_GROUP_ID = 10;

    private static final Random random = new Random();
    private static final  DecimalFormat decimalFormat = new DecimalFormat("#.##");

    //User 1
    String[] categoryArray1 = {"Miete", "Lebensmittel", "Restaurant", "Hund", "Katze"};
    ArrayList<Category> categoryList1 = new ArrayList<>();

    //User 2
    String[] categoryArray2 = {"Altersversicherung", "Hausversicherung", "Rennrad", "Basketballverein", "Musik Verein"};
    ArrayList<Category> categoryList2 = new ArrayList<>();

    //User 3
    String[] categoryArray3 = {"Zoo", "Eltern", "Zocken", "Pokern", "Glücksspiel"};
    ArrayList<Category> categoryList3 = new ArrayList<>();

    //User 4
    String[] categoryArray4 = {"Fastfood", "Hausparty", "Grillen", "Paaaaaartey", "Jobben"};
    ArrayList<Category> categoryList4 = new ArrayList<>();

    //WG
    String[] categoryArray5 = {"Haustiere", "Allgemein", "Putzen", "Zusatzausgaben", "Wurzelzahnbehandlung"};
    ArrayList<Category> categoryList5 = new ArrayList<>();

    //Sommerparty
    String[] categoryArray6 = {"Drinks", "Aufbau", "Nahrung", "Location"};
    ArrayList<Category> categoryList6 = new ArrayList<>();

    @Autowired
    public MongoTestDataCreator(MongoOperations mongoOps, GroupDocumentService groupDocumentService) throws ParseException {
        this.mongoOps = mongoOps;
        log.info("Starting mongo test data routine");
        this.groupDocumentService = groupDocumentService;
    }

    public void createTestData(){
        mongoOps.dropCollection("groupDocuments");
        mongoOps.createCollection("groupDocuments");

        addDocuments();
        fillPersonalGroups();
    }

    private void addDocuments(){
        for(int groupId = 1; groupId <= 4; groupId++) {
            var d = new GroupDocument();
            d.groupId = (long) groupId;
            GroupDocument savedGroupDoc = groupDocumentService.createDocument(d);

            addCategories(savedGroupDoc,groupId);
        }

        for (int groupId = 5; groupId<=LARGEST_GROUP_ID;groupId++){
            var d = new GroupDocument();
            d.groupId = (long) groupId;

            if(groupId == 8 || groupId == 10){
                GroupDocument savedGroupDoc = groupDocumentService.createDocument(d);
                addCategories(savedGroupDoc,groupId);
            }
            else groupDocumentService.createDocument(d);
        }
    }

    private void addCategories(GroupDocument savedGroupDoc,int groupId){
        switch (groupId){
            case 1:
                for (String s : categoryArray1) {
                    Category aCategory = new Category(s);
                    categoryList1.add(groupDocumentService.insertCategory(savedGroupDoc.groupId, aCategory));
                }
                break;
            case 2:
                for (String s : categoryArray2) {
                    Category aCategory = new Category(s);
                    categoryList2.add(groupDocumentService.insertCategory(savedGroupDoc.groupId, aCategory));
                }
                break;
            case 3 :
                for (String s : categoryArray3) {
                    Category aCategory = new Category(s);
                    categoryList3.add(groupDocumentService.insertCategory(savedGroupDoc.groupId, aCategory));
                }
                break;
            case 4 :
                for (String s : categoryArray4) {
                    Category aCategory = new Category(s);
                    categoryList4.add(groupDocumentService.insertCategory(savedGroupDoc.groupId, aCategory));
                }
                break;
            case 8:
                for (String s : categoryArray5) {
                    Category aCategory = new Category(s);
                    categoryList5.add(groupDocumentService.insertCategory(savedGroupDoc.groupId, aCategory));
                }
                break;
            case 10:
                for (String s : categoryArray6) {
                    Category aCategory = new Category(s);
                    categoryList6.add(groupDocumentService.insertCategory(savedGroupDoc.groupId, aCategory));
                }
                break;
            default : throw new IllegalStateException("Unexpected value: " + groupId);
        }
    }

    private void fillPersonalGroups(){
        Date date = new Date();
        int currentYear = date.getYear()+1900;
        Integer[] usersWithData = {1,2,3,4,8,10};
        for ( Integer userid : usersWithData) {
            for (int year = 0; year <= 2; year++) {
                for (int month = 1; month <= 12; month++) {
                    for (int day = 1; day <= 28; day += 3) {
                        createSavingEntry(month, day, currentYear - year, userid);
                    }
                }
            }
        }
    }

    private void createSavingEntry(int month, int day, int year, int userid){

        int repetitionAmount = random.nextInt(2);
        for (int repetitions = 0; repetitions<=repetitionAmount; repetitions++ ) {
            int entryNumber = month * 28 + day;
            String name = "Testentry" + entryNumber;
            String description =  "Entry number " + entryNumber;

            ArrayList<Category> usedCategoryList = matchUserToCategoryArray(userid);

            Category category = usedCategoryList.get(random.nextInt(usedCategoryList.size()));
            Date creationDate = createDate(year, month, day,random.nextInt(24));

            String postingUser = setPostingUser(userid);
            Double cost = random.nextDouble() + random.nextInt(50) ;
            String formattedDouble = decimalFormat.format(cost).replace(",",".");
            cost = Double.valueOf(formattedDouble);
            if(random.nextInt(2) == 1)
                cost = cost * -1;

            SavingEntry savingEntry = new SavingEntry(name, cost, category, postingUser, creationDate, description);
            groupDocumentService.addSavingEntry((long) userid, savingEntry);
        }

    }

    private ArrayList<Category> matchUserToCategoryArray(int userid){
        return switch (userid){
            case 1 -> categoryList1;
            case 2 -> categoryList2;
            case 3 -> categoryList3;
            case 4 -> categoryList4;
            case 8 -> categoryList5;
            case 10 -> categoryList6;
            default -> throw new IllegalStateException("Unexpected value: " + userid);
        };
    }

    private String setPostingUser(int groupId){
        String[] WG = {"demo", "annabell", "benni", "chleo"};
        String[] group9 = {"demo", "emil", "franz", "gabriella" };
        String[] Sommerparty = {"annabell", "benni", "chleo", "emil", "franz", "gabriella"};

        return switch (groupId) {
            case 1 -> "demo";
            case 2 -> "annabell";
            case 3 -> "benni";
            case 4 -> "chleo";
            case 5 -> "emil";
            case 6 -> "franz";
            case 7 -> "gabriella";
            case 8 -> WG[random.nextInt(WG.length)]; //WG
            case 9 -> group9[random.nextInt(group9.length)];
            case 10 -> Sommerparty[random.nextInt(Sommerparty.length)]; //Sommerparty
            default -> "defautlUser";
        };
    }

    private String createDatePrefixNumber(int number){
        if(number<10) return "0"+number;
        else return String.valueOf(number);
    }

    private Date createDate(int year, int month, int day, int hour){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String formattedMonth = createDatePrefixNumber(month);
        String formattedDay = createDatePrefixNumber(day);
        String formattedHour = createDatePrefixNumber(hour);
        Date creationDate;

        try {
            creationDate = formatter.parse(year+"-" + formattedMonth + "-" + formattedDay + " " + formattedHour + ":00:00");
        } catch (ParseException e) {
            creationDate = new Date();
        }

        return creationDate;
    }

}
