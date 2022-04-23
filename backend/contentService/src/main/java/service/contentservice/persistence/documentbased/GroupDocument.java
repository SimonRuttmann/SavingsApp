package service.contentservice.persistence.documentbased;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document()
public class GroupDocument {

    @Id
    Long documentId;

    Long groupId;

    List<SavingEntry> savingEntries = new ArrayList<>();

    List<Category> categories = new ArrayList<>();

}
