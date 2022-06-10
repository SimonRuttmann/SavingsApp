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
        AddSavingEntries: (state, action) => {
          action.payload.forEach( savingEntry =>
              state.push({
                  id: savingEntry.id,
                  name: savingEntry.name,
                  costBalance: savingEntry.costBalance,
                  creationDate: savingEntry.creationDate,
                  creator: savingEntry.creator,
                  description: savingEntry.description,
                  category: {id: savingEntry.category.id, name: savingEntry.category.name}
              })
          )
        },
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

export const { AddSavingEntry, RemoveSavingEntry, UpdateSavingEntry, AddSavingEntries } = savingEntrySlice.actions

export default savingEntrySlice.reducer

export const selectSavingEntryStore = (state) => state.savingEntry;

export const fetchSavingEntriesFromServer = (groupId) => (dispatch) => {
    let response = getAllGroupEntries(groupId)
    response.then(response => dispatch(AddSavingEntries(response.data)));
}

export const addSavingEntryToServer = (groupId, savingEntry) => (dispatch) => {
    let response = addGroupEntry(groupId, savingEntry)
    response.then(response => dispatch(AddSavingEntry(response.data)));
}

export const updateSavingEntryToServer = (groupId, savingEntry) => (dispatch) => {
    let response = updateGroupEntry(groupId, savingEntry)
    response.then(response => dispatch(UpdateSavingEntry(response.data)));
}

export const deleteSavingEntryFromServer = (groupId, savingEntryId) => (dispatch) => {
    let response = deleteGroupEntry(groupId, savingEntryId)
    response.then(response => dispatch(RemoveSavingEntry(response.data)));
}
