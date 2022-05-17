package dtoAndValidation.dto.processing;

import service.contentservice.validation.IValidatable;

import java.util.Date;
import java.util.List;

public class FilterInformationDTO implements IValidatable {

    private SortParameters sortParameter;

    private TimeInterval timeInterval;

    private Date startDate;

    private Date endDate;

    private List<Long> personIds;

    private List<String> categoryIds;


    public SortParameters getSortingParameter() {
        return sortParameter;
    }

    public void setSortingParameter(SortParameters sortParameters) {
        this.sortParameter = sortParameters;
    }

    public SortParameters getSortParameter() {
        return sortParameter;
    }

    public void setSortParameter(SortParameters sortParameter) {
        this.sortParameter = sortParameter;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Long> getPersonIds() {
        return personIds;
    }

    public void setPersonIds(List<Long> personIds) {
        this.personIds = personIds;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public FilterInformationDTO() {
    }

    public FilterInformationDTO(SortParameters sortParameter, Date startDate, Date endDate,
                                List<Long> personIds, List<String> categoryIds, Long groupId) {
        this.sortParameter = sortParameter;
        this.startDate = startDate;
        this.endDate = endDate;
        this.personIds = personIds;
        this.categoryIds = categoryIds;
    }
}
