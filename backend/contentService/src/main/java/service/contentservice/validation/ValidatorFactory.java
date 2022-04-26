package service.contentservice.validation;

import service.contentservice.persistence.relational.entity.Group;
import service.contentservice.persistence.relational.entity.Person;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ValidatorFactory{
    private static ValidatorFactory instance;
    private static Map<Class<?>, IValidator<?>> registry = Map.of(
            Group.class, new ValidateGroup(),
            Person.class, new ValidatePerson()
    );

    private ValidatorFactory() {}

    public static ValidatorFactory getInstance(){
        if(instance == null) return new ValidatorFactory();
        return instance;
    }


    public void registerValidator(){
        registry.put(Group.class, new ValidateGroup() );
    }

    public <T> IValidator<?> getValidator(Class<T> clazz) {
        return registry.get(clazz);
    }

}
