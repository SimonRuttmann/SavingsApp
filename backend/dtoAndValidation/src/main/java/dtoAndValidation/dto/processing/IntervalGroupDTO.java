package dtoAndValidation.dto.processing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntervalGroupDTO {

    private String dateRepresentation;

    private List<IntervalBasedEntryValueDTO> values = new ArrayList<>();

    public void addValue(IntervalBasedEntryValueDTO value){
        values.add(value);
    }

}
