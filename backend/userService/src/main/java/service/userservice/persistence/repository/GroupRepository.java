package service.userservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.userservice.persistence.entity.Group;


@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, RepositoryDetachAdapterCustom<Group> {
}
