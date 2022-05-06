package service.userservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.userservice.persistence.entity.KPerson;
import service.userservice.persistence.entity.Person;

@Repository
public interface KeycloakRepository extends JpaRepository<KPerson,String> {
    KPerson getByEmail();
}
