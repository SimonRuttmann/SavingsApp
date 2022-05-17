module documentDatabaseService {
    requires spring.data.mongodb;
    requires spring.data.commons;
    requires org.mongodb.bson;
    requires spring.context;
    requires spring.beans;
    exports documentDatabaseService.documentbased.service;
    exports documentDatabaseService.documentbased.model;
}