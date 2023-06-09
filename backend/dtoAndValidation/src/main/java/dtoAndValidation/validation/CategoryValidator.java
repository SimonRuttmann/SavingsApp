package dtoAndValidation.validation;


import dtoAndValidation.dto.content.CategoryDTO;

public class CategoryValidator implements IValidator<CategoryDTO>{

    @Override
    public boolean validate(CategoryDTO toValidate, boolean withId) {

        if(toValidate == null) return false;

        if(withId && toValidate.getId() == null) return false;
        if(!withId && toValidate.getId() != null) return false;
        if(toValidate.getName().isEmpty()) return false;
        return !toValidate.getName().isBlank();
    }
}
