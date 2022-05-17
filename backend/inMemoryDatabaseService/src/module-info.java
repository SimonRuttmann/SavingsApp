module inMemoryDatabaseService {
    requires spring.context;
    requires spring.beans;
    requires spring.data.redis;
    exports inMemoryDatabaseService.model;
    exports inMemoryDatabaseService.service;

}