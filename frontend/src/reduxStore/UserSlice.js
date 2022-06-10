import {createSlice} from '@reduxjs/toolkit'

export const userSlice = createSlice({
    name: "user",
    initialState: {value: {id: "", email: "", username: ""}},
    reducers: {
        login: (state, action) => {
            state.token = action.payload; //TODO atm, dispatch(login(token)); if object needed dispatch(login({..., token: token}); and here action.payload.token
        },                                  //TODO access via UserStore.token
        logout: (state, action) => {
            state.value = null;
        }
    }
})

export const {login, logout} = userSlice.actions

export default userSlice.reducer

export const selectUserStore = (state) => state.user;