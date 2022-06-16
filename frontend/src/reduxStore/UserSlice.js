import {createSlice} from '@reduxjs/toolkit'
import {acceptInvitation, declineInvitation, getUser, getUsernames, invite, receive} from "../api/services/User";
import {AddGroupCoreInformation} from "./GroupInformationSlice";

export const userSlice = createSlice({
    name: "user",
    initialState: {token: "", id: "", email: "", username: "", usernames: [], invitations: []},
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
        },
        setInvitations: (state, action) => {
            state.invitations = [];
            action.payload.forEach(invitation => state.invitations.push(invitation))
        },
        removeInvitation: (state, action) => {
            state.invitations = state.invitations.filter(invitation => invitation.groupId !== action.payload.groupId);
        }
    }
})

export const {login, logout, setUserData, setOtherUserNames, setInvitations,  removeInvitation} = userSlice.actions

export const fetchUserDataFromServer = () => (dispatch) => {
    let response = getUser();
    response.then(response => dispatch(setUserData(response.data)));
}

export const fetchUserNames = () => (dispatch) => {
    let response = getUsernames();
    response.then(response => dispatch(setOtherUserNames(response.data)));
}

export const fetchInvitations = () => (dispatch) => {
    let response = receive();
    response.then(response => dispatch(setInvitations(response.data)))
}

export default userSlice.reducer

export const selectUserStore = (state) => state.user;

export const selectUserNamesStore = (state) => state.usernames;

export const selectUserInvitationsStore = (state) => state.invitations;


export const declineAInvitation = (groupId) => (dispatch) => {
    return new Promise((resolve, reject) => {
        let response = declineInvitation(groupId)
        response
            .then(response => dispatch(removeInvitation(response.data)))
            .then(() => resolve(null))
            .catch(()=> reject("Error contacting server, cannot decline Invitation"))
    })
}

export const acceptAInvitation = (groupId) => (dispatch) => {
    return new Promise((resolve, reject) => {
        let response = acceptInvitation(groupId)
        response
            .then(response => dispatch(removeInvitation(response.data)))
            .then(() => resolve(null))
            .catch(()=> reject("Error contacting server, cannot accept Invtitation"))
    })
}