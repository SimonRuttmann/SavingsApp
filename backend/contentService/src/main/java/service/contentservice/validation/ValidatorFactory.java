package service.contentservice.validation;

import service.contentservice.persistence.documentbased.Category;
import service.contentservice.persistence.documentbased.SavingEntry;
import service.contentservice.persistence.relational.entity.Group;
import service.contentservice.persistence.relational.entity.Person;

import java.util.Map;

public class ValidatorFactory{
    private static ValidatorFactory instance;
    private final Map<Class<? extends IValidatable>, IValidator<?>> registry;

    private ValidatorFactory() {
        registry = Map.of(
                Group.class, new GroupValidator(),
                Person.class, new PersonValidator(),
                SavingEntry.class, new SavingEntryValidator(),
                Category.class, new CategoryValidator()
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

/*
----> Diagramm, zeigen wenn mehere clients eine große datei anfragen


CProgramm
client.c und toupperd.c
 */
