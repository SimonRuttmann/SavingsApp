package userservice.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeycloakRepository extends JpaRepository<KPerson,String> {
//    KPerson getByEmail();
}
