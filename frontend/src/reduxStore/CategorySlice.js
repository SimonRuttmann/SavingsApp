import {createSlice} from "@reduxjs/toolkit";
import {addCategory, deleteCategory, getAllCategorys, updateCategory} from "../api/services/Content";


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

export const selectCategoryStore = (state) => state.category;

export const fetchCategoriesFromServer = (groupId) => (dispatch) => {
    let response = getAllCategorys(groupId)
    response.then(response => dispatch(response.data));
}

export const addCategoryToServer = (groupId, category) => (dispatch) => {
    let response = addCategory(groupId, category)
    response.then(response => dispatch(response.data));
}

export const updateCategoryToServer = (groupId, category) => (dispatch) => {
    let response = updateCategory(groupId, category)
    response.then(response => dispatch(response.data));
}

export const deleteCategoryFromServer = (groupId, categoryId) => (dispatch) => {
    let response = deleteCategory(groupId, categoryId)
    response.then(response => dispatch(response.data));
}
