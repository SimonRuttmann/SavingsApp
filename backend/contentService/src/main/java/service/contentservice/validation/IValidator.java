package service.contentservice.validation;

public interface IValidator<T extends IValidatable>{
    boolean validate(T toValidate, boolean withId);
}
