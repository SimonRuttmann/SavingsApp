package service.contentservice.businessmodel.content.processing;

import java.util.List;

public class IntervalGroupDTO {

    private String dateRepresentation;

    private List<IntervalBasedEntryValueDTO> values;

    public void addValue(IntervalBasedEntryValueDTO value){
        values.add(value);
    }
    public String getDateRepresentation() {
        return dateRepresentation;
    }

    public void setDateRepresentation(String dateRepresentation) {
        this.dateRepresentation = dateRepresentation;
    }

    public List<IntervalBasedEntryValueDTO> getValues() {
        return values;
    }

    public void setValues(List<IntervalBasedEntryValueDTO> values) {
        this.values = values;
    }
}
