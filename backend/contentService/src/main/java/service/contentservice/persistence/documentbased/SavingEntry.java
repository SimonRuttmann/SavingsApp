package service.contentservice.persistence.documentbased;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class SavingEntry {

    @Id
    Long savingId;

    String name;

    Double costBalance;

    Category category;

    Date creationDate;

    String creator;

    String description;

}
