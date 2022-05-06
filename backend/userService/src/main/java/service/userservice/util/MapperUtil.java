package service.userservice.util;


import service.userservice.businessmodel.account.PersonDTO;
import service.userservice.persistence.entity.Person;

public class MapperUtil {




    public static PersonDTO PersonToDTO(Person person){
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setEmail(person.getEmail());
        dto.setUsername(person.getUsername());
        return dto;
    }
}
