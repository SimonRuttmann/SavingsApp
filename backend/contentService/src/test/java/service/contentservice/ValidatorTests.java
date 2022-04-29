package service.contentservice;

import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import service.contentservice.persistence.documentbased.Category;
import service.contentservice.validation.ValidatorFactory;

public class ValidatorTests {

    @Test
    public void testValidators() {
        var validator = ValidatorFactory.getInstance().getValidator(Category.class);


        Category category = new Category();
        category.name = "abc";
        Assertions.assertTrue(validator.validate(category, false));
        Assertions.assertFalse(validator.validate(category, true));

    }



}
