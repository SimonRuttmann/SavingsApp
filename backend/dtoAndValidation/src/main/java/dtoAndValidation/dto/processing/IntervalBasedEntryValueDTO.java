package dtoAndValidation.dto.processing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Used for entries inside a given time interval
 * Can represent a user or category with the sum of corresponding entries
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntervalBasedEntryValueDTO {

    private String nameDescription;

    private String id;

    private Double sum;

}
