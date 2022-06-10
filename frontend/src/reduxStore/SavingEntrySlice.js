import {createSlice} from "@reduxjs/toolkit";
import {addGroupEntry, deleteGroupEntry, getAllGroupEntries, updateGroupEntry} from "../api/services/Content";

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

const savingEntrySlice = createSlice({
    name: 'savingEntry',
    initialState: [],
    reducers: {
        AddSavingEntry: (state, action) => {
            state.push({
                id: action.payload.id,
                name: action.payload.name,
                costBalance: action.payload.costBalance,
                creationDate: action.payload.creationDate,
                creator: action.payload.creator,
                description: action.payload.description,
                category: {id: action.payload.category.id, name: action.payload.category.name}
            });
        },
        RemoveSavingEntry: (state, action) => {
            state = state.filter(savingEntry => savingEntry.id !== action.payload.id);
        },
        UpdateSavingEntry: (state, action) => {
            let savingEntry = state.find(savingEntry => savingEntry.id === action.payload.id);
            savingEntry.name = action.payload.name;
            savingEntry.costBalance = action.payload.costBalance;
            savingEntry.creationDate = action.payload.creationDate;
            savingEntry.creator = action.payload.creator;
            savingEntry.description = action.payload.description;
            savingEntry.category = {id: action.payload.category.id, name: action.payload.category.name}
        }
    }
})

export const { AddSavingEntry, RemoveSavingEntry, UpdateSavingEntry } = savingEntrySlice.actions

export default savingEntrySlice.reducer

export const selectSavingEntryStore = (state) => state.savingEntry;

export const fetchSavingEntriesFromServer = (header, groupId) => (dispatch) => {
    let response = getAllGroupEntries(groupId, header)
    response.then(response => dispatch(response.data));
}

export const addSavingEntryToServer = (header, groupId, savingEntry) => (dispatch) => {
    let response = addGroupEntry(groupId, savingEntry, header)
    response.then(response => dispatch(response.data));
}

export const updateSavingEntryToServer = (header, groupId, savingEntry) => (dispatch) => {
    let response = updateGroupEntry(groupId, savingEntry, header)
    response.then(response => dispatch(response.data));
}

export const deleteSavingEntryFromServer = (header, groupId, savingEntryId) => (dispatch) => {
    let response = deleteGroupEntry(groupId, savingEntryId, header)
    response.then(response => dispatch(response.data));
}
