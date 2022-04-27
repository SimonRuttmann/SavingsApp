package service.contentservice;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import service.contentservice.persistence.documentbased.Category;
import service.contentservice.validation.ValidatorFactory;

public class ValidatorTests {

    @Test
    public void testValidators() {
        var validator = ValidatorFactory.getInstance().getValidator(Category.class);


        Assertions.assertTrue(validator.validate(new Category("abc"), false));
        Assertions.assertFalse(validator.validate(new Category("abc"), true));

    }



}
