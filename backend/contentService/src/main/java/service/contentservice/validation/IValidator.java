package service.contentservice.validation;

import java.util.List;

//add interface persistence enttity T extends persitenceEntity, in Factory anpassen --> Class <? extends Persistence Enttity>
public interface IValidator<T>{
    List<String> validate(T toValidate);
}
