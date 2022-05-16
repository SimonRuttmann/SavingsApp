package service.userservice.persistence.repository.userdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.userservice.persistence.entity.userdata.*;

import java.util.List;
import java.util.UUID;


@Repository
public interface InvitationRepository extends JpaRepository<Invitation, InvitationCompoundId>, RepositoryDetachAdapterCustom<Invitation>{
    List<Invitation> findByInvitedPersonAndInvitationStatus(Person person, InvitationStatus status);
    List<Invitation> findByInvitedPersonAndRequestedGroup(Person person, Group group);
}
