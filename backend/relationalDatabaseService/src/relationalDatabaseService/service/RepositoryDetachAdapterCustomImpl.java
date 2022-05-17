package relationalDatabaseService.service;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RepositoryDetachAdapterCustomImpl<T> implements RepositoryDetachAdapterCustom<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void detach(T o) {
        if(isDetached(o)) return;
        entityManager.detach(o);
    }

    @Override
    public void attach(T o) {entityManager.merge(o); }

    @Override
    public boolean isDetached(T o) { return entityManager.contains(o); }
}
