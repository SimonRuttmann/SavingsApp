import {createSlice} from '@reduxjs/toolkit'
import {getSavingEntriesProcessed} from "../api/services/Content";

export const ProcessingSlice = createSlice({
    name: "processing",
    initialState: {balanceProcessResultDTO: {}, diagramByIntervalAndCategory: {}, diagramByIntervalAndCreator: {}, savingEntryDTOs: []},
    reducers: {
        updateProcessingData: (state, action) => {
            state.balanceProcessResultDTO = action.payload.balanceProcessResultDTO;
            state.diagramByIntervalAndCategory = action.payload.diagramByIntervalAndCategory;
            state.diagramByIntervalAndCreator = action.payload.diagramByIntervalAndCreator;
            state.savingEntryDTOs = action.payload.savingEntryDTOs;
        },
        removeSortedAndFilteredSavingEntry: (state, action) => {

            state.savingEntryDTOs = state.savingEntryDTOs.filter(savingEntry => savingEntry.id !== action.payload);

        },
    }
});

export const {updateProcessingData, removeSortedAndFilteredSavingEntry} = ProcessingSlice.actions

export default ProcessingSlice.reducer

export const selectProcessingStore = (state) => state.processing;

export const fetchProcessingResultsFromServer = (groupId, filterInformation) => (dispatch) => {
    let response = getSavingEntriesProcessed(groupId, filterInformation)
    response.then(response => dispatch(updateProcessingData(response.data)));
}