import {createSlice} from '@reduxjs/toolkit'

export const userSlice = createSlice({
    name: "user",
    initialState: {value: {id: "", email: "", username: ""}},
    reducers: {
        login: (state, action) => {
            state.value = action.payload
            console.log(state.value)
        },
        logout: (state, action) => {
            state.value = {
                id: "",
                email: "",
                username: ""
            };
        }
    }
});

export const {login, logout} = userSlice.actions

export default userSlice.reducer