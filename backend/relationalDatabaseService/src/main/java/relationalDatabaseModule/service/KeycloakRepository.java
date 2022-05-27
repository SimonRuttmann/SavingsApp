package relationalDatabaseModule.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import relationalDatabaseModule.model.KPerson;
import relationalDatabaseModule.model.Person;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface KeycloakRepository extends JpaRepository<KPerson,String>, RepositoryDetachAdapterCustom<KPerson>{
    @Query(value = "SELECT s FROM KPerson As s WHERE s.username = ?1")
    Optional<Person> findPersonByUsername(String username);

    @Query(value = "SELECT s FROM KPerson As s WHERE s.email = ?1")
    Optional<Person> findPersonByEmail(String email);

    @Query(value = "SELECT s FROM KPerson As s WHERE s.id = ?1")
    Optional<Person> findPersonById(String id);
}
