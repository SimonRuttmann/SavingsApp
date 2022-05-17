module relationalDatabaseService {
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires spring.beans;
    requires spring.context;
    requires java.transaction;
    requires spring.data.jpa;
    requires spring.web;
    exports relationalDatabaseService.model;
    exports relationalDatabaseService.service;
}