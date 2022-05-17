package service.userservice.validation;


import service.userservice.businessmodel.account.GroupDTO;
import service.userservice.businessmodel.account.InviteDTO;
import service.userservice.businessmodel.account.PersonDTO;

import java.util.Map;

public class ValidatorFactory {
    private static ValidatorFactory instance;
    private final Map<Class<?extends service.userservice.validation.IValidatable>, service.userservice.validation.IValidator<?>> registry;

    private ValidatorFactory() {
        registry = Map.of(
                GroupDTO.class, new GroupValidator(),
                PersonDTO.class, new PersonValidator(),
                InviteDTO.class, new InviteValidator()
        );
    }

    public static ValidatorFactory getInstance(){
        if(instance == null) instance = new ValidatorFactory();
        return instance;
    }
    public <T extends IValidatable> service.userservice.validation.IValidator<T> getValidator(Class<T> clazz) {
        service.userservice.validation.IValidator<T> validator;
        try {
            //noinspection unchecked
            validator = (IValidator<T>) registry.get(clazz);
        } catch (ClassCastException e) {
            validator = null;
        }
        return validator;
    }

}