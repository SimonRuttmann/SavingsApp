package dtoAndValidation.validation;

/**
 * Validators implement this interface with the type it validates
 *
 * @param <T> The validatable type to validate
 * @see IValidatable
 */
public interface IValidator<T extends IValidatable>{
    boolean validate(T toValidate, boolean withId);
}
