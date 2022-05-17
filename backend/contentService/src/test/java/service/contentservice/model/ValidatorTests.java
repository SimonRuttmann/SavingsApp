package service.contentservice.model;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import service.contentservice.businessmodel.content.CategoryDTO;
import service.contentservice.validation.ValidatorFactory;

public class ValidatorTests {

    @Test
    public void testValidators() {
        var validator = ValidatorFactory.getInstance().getValidator(CategoryDTO.class);


        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("abc");
        Assertions.assertTrue(validator.validate(categoryDTO, false));
        Assertions.assertFalse(validator.validate(categoryDTO, true));

    }



}
