module inMemoryDatabaseService {
    requires spring.context;
    requires spring.beans;
    requires spring.data.redis;
    requires java.annotation;

    exports service;
    exports model;
}