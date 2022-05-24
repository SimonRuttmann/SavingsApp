package dtoAndValidation.validation;


import dtoAndValidation.dto.processing.FilterInformationDTO;

public class FilterInformationValidator implements IValidator<FilterInformationDTO> {

    @Override
    public boolean validate(FilterInformationDTO toValidate, boolean withId) {
        if(toValidate.getSortingParameter() == null) return false;
        return toValidate.getTimeInterval() != null;
    }

}
