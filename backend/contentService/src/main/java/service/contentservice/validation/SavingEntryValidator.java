package service.contentservice.validation;

import service.contentservice.businessmodel.content.CategoryDTO;
import service.contentservice.businessmodel.content.SavingEntryDTO;
import service.contentservice.persistence.documentbased.Category;

public class SavingEntryValidator implements IValidator<SavingEntryDTO> {

    @Override
    public boolean validate(SavingEntryDTO toValidate, boolean withId) {

        if(toValidate == null) return false;

        if(withId && toValidate.getId() == null) return false;
        if(!withId && toValidate.getId() != null) return false;

        if(toValidate.getName() == null) return false;
        if(toValidate.getName().isBlank()) return false;

        if(toValidate.getCostBalance() == null) return false;

        if(toValidate.getCategory() == null) return false;
        if(!ValidatorFactory.
                getInstance().
                getValidator(CategoryDTO.class).
                validate(toValidate.getCategory(), withId))
            return false;

        //Description is optional

        if(toValidate.getCreator() == null) return false;
        return !toValidate.getCreator().isBlank();

    }

}
