package dtoAndValidation.validation;

import dtoAndValidation.dto.content.CategoryDTO;
import dtoAndValidation.dto.content.SavingEntryDTO;
import dtoAndValidation.dto.processing.FilterInformationDTO;
import dtoAndValidation.dto.user.GroupDTO;
import dtoAndValidation.dto.user.PersonDTO;

import java.util.Map;

public class ValidatorFactory{
    private static ValidatorFactory instance;
    private final Map<Class<? extends IValidatable>, IValidator<?>> registry;

    private ValidatorFactory() {
        registry = Map.of(
                GroupDTO.class, new GroupValidator(),
                PersonDTO.class, new PersonValidator(),
                SavingEntryDTO.class, new SavingEntryValidator(),
                CategoryDTO.class, new CategoryValidator(),
                FilterInformationDTO.class, new FilterInformationValidator()
        );
    }

    public static ValidatorFactory getInstance(){
        if(instance == null) instance = new ValidatorFactory();
        return instance;
    }
    public <T extends IValidatable> IValidator<T> getValidator(Class<T> clazz) {
        IValidator<T> validator;
        try {
            //noinspection unchecked
            validator = (IValidator<T>) registry.get(clazz);
        } catch (ClassCastException e) {
            validator = null;
        }
        return validator;
    }

}