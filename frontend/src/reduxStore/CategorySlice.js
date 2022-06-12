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
        AddCategories: (state, action) => {
            console.log("update categories")
            state = [];
            action.payload.forEach(category => {
                state.push({
                    id: category.id,
                    name: category.name
                })
            })
            return state;
        },
        AddCategory: (state, action) => {
            state.push({
                id: action.payload.id,
                name: action.payload.name
            })
        },
        RemoveCategory: (state, action) => {
            return state.filter(category => category.id !== action.payload);
        },
        UpdateCategory: (state, action) => {
            let category = state.find(category => category.id === category.payload.id)
            category.name = action.payload.name;
        }
    }
})

export const { AddCategory, RemoveCategory, UpdateCategory, AddCategories } = categorySlice.actions

export default categorySlice.reducer

export const selectCategoryStore = (state) => state.category;

export const fetchCategoriesFromServer = (groupId) => (dispatch) => {

    return new Promise( (resolve, reject) => {
        let response = getAllCategorys(groupId)
        response
            .then(response => dispatch(AddCategories(response.data)))
            .then(() => resolve(null))
            .catch(() => reject("Error contacting server, cannot fetch categories"))
    })

}

export const addCategoryToServer = (groupId, category) => (dispatch) => {

    return new Promise( (resolve, reject) => {
        let response = addCategory(groupId, category)
        response
            .then(response => dispatch(AddCategory(response.data)))
            .then(() => resolve(null))
            .catch(() => reject("Error contacting server, add category"))
    })
}

export const updateCategoryToServer = (groupId, category) => (dispatch) => {

    return new Promise( (resolve, reject) => {
        let response = updateCategory(groupId, category)
        response
            .then(response => dispatch(UpdateCategory(response.data)))
            .then(() => resolve(null))
            .catch(() => reject("Error contacting server, cannot update category"))
    })
}

export const deleteCategoryFromServer = (groupId, categoryId) => (dispatch) => {
    return new Promise( (resolve, reject) => {
        let response = deleteCategory(groupId, categoryId)
        response
            .then(dispatch(RemoveCategory(categoryId)))
            .then(() => resolve(null))
            .catch(() => reject("Error contacting server, cannot remove category"))
    })
}
