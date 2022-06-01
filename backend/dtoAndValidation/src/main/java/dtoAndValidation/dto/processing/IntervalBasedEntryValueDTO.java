package dtoAndValidation.dto.processing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Used as entries inside a given time interval
 * Can represent a user or category with the sum of corresponding entries
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntervalBasedEntryValueDTO {

    private String nameDescription;

    private Double sum;

}
