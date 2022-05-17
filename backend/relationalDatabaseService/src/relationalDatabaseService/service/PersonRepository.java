package relationalDatabaseService.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import service.userservice.persistence.entity.userdata.Person;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID>, RepositoryDetachAdapterCustom<Person>{

    @Query(value = "SELECT s FROM Person As s WHERE s.username = ?1")
    Optional<Person> findPersonByUsername(String username);

    @Query(value = "SELECT s FROM Person As s WHERE s.email = ?1")
    Optional<Person> findPersonByEmail(String email);

    @Query(value = "SELECT s FROM Person As s WHERE s.id = ?1")
    Optional<Person> findPersonById(UUID id);

}
