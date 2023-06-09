package relationalDatabaseModule.service;

import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryDetachAdapterCustom<T> {

    void detach(T o);

    void attach(T o);


    boolean isDetached(T o);
}
