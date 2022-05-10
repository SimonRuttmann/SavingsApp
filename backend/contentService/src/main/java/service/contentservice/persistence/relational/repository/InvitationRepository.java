package service.contentservice.persistence.relational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.contentservice.persistence.relational.entity.Invitation;
import service.contentservice.persistence.relational.entity.InvitationCompoundId;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, InvitationCompoundId>, RepositoryDetachAdapterCustom<Invitation>{
}
