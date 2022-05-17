package relationalDatabaseService.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import relationalDatabaseService.model.*;

import java.util.List;


@Repository
public interface InvitationRepository extends JpaRepository<Invitation, InvitationCompoundId>, RepositoryDetachAdapterCustom<Invitation>{
    List<Invitation> findByInvitedPersonAndInvitationStatus(Person person, InvitationStatus status);
    List<Invitation> findByInvitedPersonAndRequestedGroup(Person person, Group group);
}
