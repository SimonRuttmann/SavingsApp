package service.contentservice.businessmodel.content.processing;

import service.contentservice.businessmodel.content.SavingEntryDTO;

import java.util.ArrayList;
import java.util.List;

public class ProcessResultContainerDTO {

    private List<SavingEntryDTO> savingEntryDTOs = new ArrayList<>();

    private BalanceProcessResultDTO balanceProcessResultDTO;

    private List<IntervalGroupDTO> diagramByIntervalAndCategory;

    private List<IntervalGroupDTO> diagramByIntervalAndCreator;

    public void addSavingEntry(SavingEntryDTO entry){
        savingEntryDTOs.add(entry);
    }

    public void removeSavingEntry(SavingEntryDTO entry){
        savingEntryDTOs.remove(entry);
    }

    public List<SavingEntryDTO> getSavingEntryDTOs() {
        return savingEntryDTOs;
    }

    public void setSavingEntryDTOs(List<SavingEntryDTO> savingEntryDTOs) {
        this.savingEntryDTOs = savingEntryDTOs;
    }

    public BalanceProcessResultDTO getBalanceProcessResultDTO() {
        return balanceProcessResultDTO;
    }
    public void setBalanceProcessResultDTO(BalanceProcessResultDTO balanceProcessResultDTO) {
        this.balanceProcessResultDTO = balanceProcessResultDTO;
    }

    public List<IntervalGroupDTO> getDiagramByIntervalAndCategory() {
        return diagramByIntervalAndCategory;
    }

    public void setDiagramByIntervalAndCategory(List<IntervalGroupDTO> diagramByIntervalAndCategory) {
        this.diagramByIntervalAndCategory = diagramByIntervalAndCategory;
    }

    public List<IntervalGroupDTO> getDiagramByIntervalAndCreator() {
        return diagramByIntervalAndCreator;
    }

    public void setDiagramByIntervalAndCreator(List<IntervalGroupDTO> diagramByIntervalAndCreator) {
        this.diagramByIntervalAndCreator = diagramByIntervalAndCreator;
    }
}
