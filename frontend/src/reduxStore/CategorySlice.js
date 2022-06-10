import {createSlice} from "@reduxjs/toolkit";

/**
 *  Category Schema
 *  String id,
 *  String name
 */

const categorySlice = createSlice({
    name: 'category',
    initialState: [],
    reducers: {
        AddCategory: (state, action) => {
            state.push({
                id: action.payload.id,
                name: action.payload.name
            })
        },
        RemoveCategory: (state, action) => {
            state = state.filter(category => category.id !== action.payload.id);
        },
        UpdateCategory: (state, action) => {
            let category = state.find(category => category.id === category.payload.id)
            category.name = action.payload.name;
        }
    }
})

export const { AddCategory, RemoveCategory, UpdateCategory } = categorySlice.actions
export default categorySlice.reducer