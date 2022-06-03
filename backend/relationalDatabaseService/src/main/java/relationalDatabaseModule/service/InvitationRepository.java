package relationalDatabaseModule.service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import relationalDatabaseModule.model.*;


import java.util.List;


@Repository
public interface InvitationRepository extends JpaRepository<Invitation, InvitationCompoundId>, RepositoryDetachAdapterCustom<Invitation>{
    List<Invitation> findByInvitedPersonAndInvitationStatus(KPerson person, InvitationStatus status);
    List<Invitation> findByInvitedPersonAndRequestedGroup(KPerson person, Group group);
}
