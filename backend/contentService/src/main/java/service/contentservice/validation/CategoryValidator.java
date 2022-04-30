package service.contentservice.validation;

import service.contentservice.businessmodel.content.CategoryDTO;
import service.contentservice.persistence.documentbased.Category;

public class CategoryValidator implements IValidator<CategoryDTO>{

    @Override
    public boolean validate(CategoryDTO toValidate, boolean withId) {

        if(toValidate == null) return false;

        if(withId && toValidate.getId() == null) return false;
        if(!withId && toValidate.getId() != null) return false;

        if(toValidate.getId() == null) return false;
        return !toValidate.getName().isBlank();
    }
}
