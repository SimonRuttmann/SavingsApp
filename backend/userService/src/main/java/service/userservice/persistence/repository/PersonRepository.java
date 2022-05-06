package service.userservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import service.userservice.persistence.entity.Person;


import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long>, RepositoryDetachAdapterCustom<Person>{

    @Query(value = "SELECT s FROM Person As s WHERE s.username = ?1")
    Optional<Person> findPersonByUsername(String username);

    @Query(value = "SELECT s FROM Person As s WHERE s.email = ?1")
    Optional<Person> findPersonByEmail(String email);


}
