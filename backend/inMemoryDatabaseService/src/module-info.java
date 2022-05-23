module inMemoryDatabaseService {
    requires spring.context;
    requires spring.beans;
    requires spring.data.redis;
    requires java.annotation;
    exports inMemoryDatabaseService.model;
    exports inMemoryDatabaseService.service;

}