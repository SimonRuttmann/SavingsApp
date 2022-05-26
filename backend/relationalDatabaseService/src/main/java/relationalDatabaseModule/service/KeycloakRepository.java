package relationalDatabaseModule.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import relationalDatabaseModule.model.KPerson;

@Repository
public interface KeycloakRepository extends JpaRepository<KPerson,String> {
//    KPerson getByEmail();
}
