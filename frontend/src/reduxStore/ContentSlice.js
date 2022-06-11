import {createSlice} from '@reduxjs/toolkit'
import {addGroupEntry, deleteGroupEntry, getSavingEntriesProcessed, updateGroupEntry} from "../api/services/Content";


/**
 * Saving Entry Schema
 * string id,
 * string name,
 * double costBalance
 * date creationDate
 * string creator
 * string description
 *
 * categoryDTO category
 * @see CategorySlice
 */

export const ContentSlice = createSlice({
    name: "content",
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
        addSavingEntry: (state, action) => {
            state.savingEntryDTOs.push({
                id: action.payload.id,
                name: action.payload.name,
                costBalance: action.payload.costBalance,
                creationDate: action.payload.creationDate,
                creator: action.payload.creator,
                description: action.payload.description,
                category: {id: action.payload.category.id, name: action.payload.category.name}
            });
        },
        removeSavingEntry: (state, action) => {

            state.savingEntryDTOs = state.savingEntryDTOs.filter(savingEntry => savingEntry.id !== action.payload);
        },
        updateSavingEntry: (state, action) => {
            let savingEntry = state.savingEntryDTOs.find(savingEntry => savingEntry.id === action.payload.id);
            savingEntry.name = action.payload.name;
            savingEntry.costBalance = action.payload.costBalance;
            savingEntry.creationDate = action.payload.creationDate;
            savingEntry.creator = action.payload.creator;
            savingEntry.description = action.payload.description;
            savingEntry.category = {id: action.payload.category.id, name: action.payload.category.name}
        }
    }
});


export const fetchProcessingResultsFromServer = (groupId, filterInformation) => (dispatch) => {
    let response = getSavingEntriesProcessed(groupId, filterInformation)
    response.then(response => dispatch(updateProcessingData(response.data)));
}

export const addSavingEntryToServer = (groupId, savingEntry) => (dispatch) => {
    return new Promise((resolve, reject) => {
        let response = addGroupEntry(groupId, savingEntry)
        response
            .then(response => dispatch(addSavingEntry(response.data)))
            .then(() => resolve(null))
            .catch(()=> reject("Error contacting server, cannot add saving entry"))
    });
}

export const updateSavingEntryToServer = (groupId, savingEntry) => (dispatch) => {
    return new Promise( (resolve, reject) => {
        let response = updateGroupEntry(groupId, savingEntry)
        response
            .then(response => dispatch(updateSavingEntry(response.data)))
            .then(() => resolve(null))
            .catch(() => reject("Error contacting server, cannot update saving entry"))
    })
}

export const deleteSavingEntryFromServer = (groupId, savingEntryId) => (dispatch) => {

    return new Promise( (resolve, reject) => {
        let response = deleteGroupEntry(groupId, savingEntryId)
        response
            .then(() => dispatch(removeSavingEntry(savingEntryId)))
            .then(() => resolve(null))
            .catch(() => reject("Error contacting server, cannot delete saving entry"))
    });
}

export const {updateProcessingData, removeSortedAndFilteredSavingEntry, addSavingEntry, updateSavingEntry, removeSavingEntry} = ContentSlice.actions
export default ContentSlice.reducer
export const selectProcessingStore = (state) => state.content;
