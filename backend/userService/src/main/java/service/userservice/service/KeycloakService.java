package service.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.userservice.persistence.entity.KPerson;
import service.userservice.persistence.entity.Person;
import service.userservice.persistence.repository.GroupRepository;
import service.userservice.persistence.repository.InvitationRepository;
import service.userservice.persistence.repository.KeycloakRepository;
import service.userservice.persistence.repository.PersonRepository;

import java.util.Optional;

@Service
public class KeycloakService {

    private final KeycloakRepository keycloakRepository;

    @Autowired
    public KeycloakService(
            KeycloakRepository keycloakRepository) {

        this.keycloakRepository = keycloakRepository;
    }

    public String getPersonById(String id){
        Optional<KPerson> person = keycloakRepository.findById(id);
        if(person.isEmpty()) return null;
        //keycloakRepository.detach(person.get());
        return person.get().getId();
    }
}
