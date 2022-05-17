package relationalDatabaseService.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.userservice.persistence.entity.userdata.Group;
import service.userservice.persistence.entity.userdata.Person;


@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, RepositoryDetachAdapterCustom<Group> {
}
