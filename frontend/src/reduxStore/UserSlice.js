import {createSlice} from '@reduxjs/toolkit'

export const userSlice = createSlice({
    name: "user",
    initialState: {value: {id: "", email: "", username: ""}},
    reducers: {
        login: (state, action) => {
            state.value = action.payload
        },
        logout: (state, action) => {
            state.value = null;
        }
    }
})

export const {login, logout} = userSlice.actions
export default userSlice.reducer