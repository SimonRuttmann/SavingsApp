package service.contentservice.persistence.relational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.contentservice.persistence.relational.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, RepositoryDetachAdapterCustom<Group> {
}
