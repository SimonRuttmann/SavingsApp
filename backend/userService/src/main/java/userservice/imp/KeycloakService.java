package userservice.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userservice.persistence.KPerson;
import userservice.persistence.KeycloakRepository;


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
