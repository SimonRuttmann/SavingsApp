import {createSlice} from '@reduxjs/toolkit'
import {getSavingEntriesProcessed} from "../api/services/Content";

export const ProcessingSlice = createSlice({
    name: "processing",
    initialState: {value: {statistic1: {}, statistic2: {}, statistic3: {}}},
    reducers: {
        updateProcessingData: (state, action) => {
            state.value = action.payload
        },
    }
});

export const {updateProcessingData} = ProcessingSlice.actions

export default ProcessingSlice.reducer

export const selectProcessingStore = (state) => state.processing;

export const fetchProcessingResultsFromServer = (header, groupId, filterInformation) => (dispatch) => {
    let response = getSavingEntriesProcessed(groupId, filterInformation, header)
    response.then(response => dispatch(updateProcessingData(response.data)));
}