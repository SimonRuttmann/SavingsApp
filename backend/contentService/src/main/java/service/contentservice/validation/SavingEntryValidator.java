package service.contentservice.validation;

import service.contentservice.persistence.documentbased.Category;
import service.contentservice.persistence.documentbased.SavingEntry;

import java.util.Comparator;
import java.util.List;

public class SavingEntryValidator implements IValidator<SavingEntry> {

    @Override
    public boolean validate(SavingEntry toValidate, boolean withId) {

        if(toValidate == null) return false;

    //    if(withId && toValidate.savingId == null) return false;
       // if(!withId && toValidate.savingId != null) return false;

        if(toValidate.name == null) return false;
        if(toValidate.name.isBlank()) return false;

        if(toValidate.costBalance == null) return false;

      //  if(toValidate.category == null) return false;
     //   if(!ValidatorFactory.getInstance().getValidator(Category.class).
     //           validate(toValidate.category, withId)) return false;

        //Description is optional

        if(toValidate.creator == null) return false;
        return !toValidate.creator.isBlank();

    }

}
