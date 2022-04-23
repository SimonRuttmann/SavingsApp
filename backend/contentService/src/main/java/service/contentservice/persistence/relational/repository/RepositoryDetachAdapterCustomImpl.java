package service.contentservice.persistence.relational.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RepositoryDetachAdapterCustomImpl<T> implements RepositoryDetachAdapterCustom<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void detach(T o) {
        entityManager.detach(o);
    }

    @Override
    public void attach(T o) {entityManager.merge(o); }
}
