package dtoAndValidation.dto.content;

import dtoAndValidation.dto.user.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralGroupInformationDTO {

    private List<PersonDTO> personDTOList = new ArrayList<>();

    private String groupName;

    public void addPersonToGroupInfo(PersonDTO person){
        personDTOList.add(person);
    }

}
