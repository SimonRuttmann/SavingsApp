package service.contentservice.validation;

import service.contentservice.businessmodel.account.GroupDTO;
import service.contentservice.businessmodel.account.PersonDTO;
import service.contentservice.businessmodel.content.CategoryDTO;
import service.contentservice.businessmodel.content.SavingEntryDTO;
import service.contentservice.businessmodel.content.processing.FilterInformationDTO;

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