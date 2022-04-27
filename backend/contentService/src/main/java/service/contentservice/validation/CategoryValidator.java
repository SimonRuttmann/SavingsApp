package service.contentservice.validation;

import service.contentservice.persistence.documentbased.Category;

import java.util.List;

public class CategoryValidator implements IValidator<Category>{

    @Override
    public boolean validate(Category toValidate, boolean withId) {

        if(toValidate == null) return false;

        if(withId && toValidate.id == null) return false;
        if(!withId && toValidate.id != null) return false;

        if(toValidate.name == null) return false;
        return !toValidate.name.isBlank();
    }
}
