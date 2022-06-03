package dtoAndValidation.dto.processing;


import dtoAndValidation.validation.IValidatable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterInformationDTO implements IValidatable {

    private SortParameters sortParameter;

    private TimeInterval timeInterval;

    private Date startDate;

    private Date endDate;

    private List<UUID> personIds;

    private List<String> categoryIds;

}
