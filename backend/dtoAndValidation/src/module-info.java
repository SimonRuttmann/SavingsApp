module dtoAndValidation {
    requires documentDatabaseService;
    requires relationalDatabaseService;
    exports dtoAndValidation.util;
    exports dtoAndValidation.validation;
    exports dtoAndValidation.dto.user;
    exports dtoAndValidation.dto.content;
    exports dtoAndValidation.dto.processing;
}