package dtoAndValidation.dto.processing;

/**
 * Used as entries inside a given time interval
 * Can represent a user or category with the sum of corresponding entries
 */
public class IntervalBasedEntryValueDTO {

    private String nameDescription;

    private Double sum;

    public String getNameDescription() {
        return nameDescription;
    }

    public void setNameDescription(String nameDescription) {
        this.nameDescription = nameDescription;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
