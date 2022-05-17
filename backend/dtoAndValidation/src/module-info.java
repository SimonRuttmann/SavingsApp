module dtoAndValidation {
    requires documentDatabaseService;
    requires relationalDatabaseService;
    requires spring.web;
    requires spring.context;
    exports dtoAndValidation.util;
    exports dtoAndValidation.validation;
    exports dtoAndValidation.dto.user;
    exports dtoAndValidation.dto.content;
    exports dtoAndValidation.dto.processing;
}