package dtoAndValidation.dto.processing;

import dtoAndValidation.dto.content.SavingEntryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessResultContainerDTO {

    private List<SavingEntryDTO> savingEntryDTOs = new ArrayList<>();

    private BalanceProcessResultDTO balanceProcessResultDTO;

    private List<IntervalGroupDTO> diagramByIntervalAndCategory;

    private List<IntervalGroupDTO> diagramByIntervalAndCreator;

    public void addSavingEntry(SavingEntryDTO entry){
        savingEntryDTOs.add(entry);
    }

}
