package relationalDatabaseModule.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import relationalDatabaseModule.model.KPerson;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface KeycloakRepository extends JpaRepository<KPerson,String>, RepositoryDetachAdapterCustom<KPerson>{
    @Query(value = "SELECT s FROM KPerson As s WHERE s.username = ?1")
    Optional<KPerson> findPersonByUsername(String username);

    @Query(value = "SELECT s FROM KPerson As s WHERE s.email = ?1")
    Optional<KPerson> findPersonByEmail(String email);

    @Query(value = "SELECT s FROM KPerson As s WHERE s.id = ?1")
    Optional<KPerson> findPersonById(String id);
}
