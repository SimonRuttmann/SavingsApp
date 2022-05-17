package service.userservice.persistence.repository.keycloak;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.userservice.persistence.entity.keycloak.KPerson;

@Repository
public interface KeycloakRepository extends JpaRepository<KPerson,String> {
//    KPerson getByEmail();
}
