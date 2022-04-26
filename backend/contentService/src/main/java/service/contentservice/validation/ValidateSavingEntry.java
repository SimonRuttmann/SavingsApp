package service.contentservice.validation;

import service.contentservice.persistence.documentbased.SavingEntry;

import java.util.Comparator;

public class ValidateSavingEntry implements Comparator<String>, IValidateRequestObject<SavingEntry> {
    @Override
    public int compare(String s1, String s2) {
        return s1.compareToIgnoreCase(s2);
    }

}
