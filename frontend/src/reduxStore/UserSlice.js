import {createSlice} from '@reduxjs/toolkit'
import {getUser, getUsernames} from "../api/services/User";

export const userSlice = createSlice({
    name: "user",
    initialState: {token: "", id: "", email: "", username: "", usernames: []},
    reducers: {
        login: (state, action) => {
            state.token = action.payload; //TODO atm, dispatch(login(token)); if object needed dispatch(login({..., token: token}); and here action.payload.token
        },                                  //TODO access via UserStore.token
        logout: (state, action) => {
            state = null;
        },
        setUserData: (state, action) => {
            state.id = action.payload.id;
            state.email = action.payload.email;
            state.username = action.payload.username;
        },
        setOtherUserNames: (state, action) => {
              state.usernames = [];
              action.payload.forEach(username => state.usernames.push(username))
        }
    }
})

export const {login, logout, setUserData, setOtherUserNames} = userSlice.actions

export const fetchUserDataFromServer = () => (dispatch) => {
    let response = getUser();
    response.then(response => dispatch(setUserData(response.data)));
}

export const fetchUserNames = () => (dispatch) => {
    let response = getUsernames();
    response.then(response => dispatch(setOtherUserNames(response.data)))
}

export default userSlice.reducer

export const selectUserStore = (state) => state.user;