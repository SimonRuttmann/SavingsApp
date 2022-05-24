package dtoAndValidation.dto.content;

import dtoAndValidation.dto.user.PersonDTO;

import java.util.List;

public class GeneralGroupInformationDTO {

    private List<PersonDTO> personDTOList;

    private String groupName;

    public void addPersonToGroupInfo(PersonDTO person){
        personDTOList.add(person);
    }

    public List<PersonDTO> getPersonDTOList() {
        return personDTOList;
    }

    public void setPersonDTOList(List<PersonDTO> personDTOList) {
        this.personDTOList = personDTOList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
