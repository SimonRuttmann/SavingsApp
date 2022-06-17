import {createSlice} from "@reduxjs/toolkit";
import {getGroupInfo} from "../api/services/Content";
import {deleteGroup, getGroup, leaveGroup, register} from "../api/services/User";


const groupInformationSlice = createSlice({
    name: 'groupInformation',
    initialState: [],
    reducers: {
        //Based on UserService - GroupController userservice/group/
        //Long id, String groupName, boolean personGroup
        AddGroupCoreInformation: (state, action) => {
            action.payload.forEach( group => {
                if( state.find(g => g.id === group.id) != null) return
                state.push({
                    id: group.id,
                    groupName: group.groupName,
                    personGroup: group.personGroup
                })
            })

        },
        //Based on ContentService - ProcessingController /content/processing/{groupId}
        //List<PersonDTO> personDTOList, StringGroupName
        AddGeneralInformationToGroup: (state, action) => {
            let group = state.find(group => group.id === action.payload.id)
            group.groupName = action.payload.groupName;
            group.personDTOList = action.payload.personDTOList;
        },
        AddGroup: (state, action) => {
            state.push({
                id: action.payload.id,
                groupName: action.payload.groupName,
                personGroup: action.payload.personGroup
            })
        },
        RemoveGroup: (state, action) => {
            return state.filter(group => group.id !== action.payload.id);
        },
    }
})

export const { AddGroupCoreInformation, AddGeneralInformationToGroup, AddGroup, RemoveGroup } = groupInformationSlice.actions

export default groupInformationSlice.reducer

export const selectGroupInformationStore = (state) => state.groupInformation;


export const fetchGroupCoreInformationFromServer = () => (dispatch) => {

    return new Promise((resolve, reject) => {
        let response = getGroup()
        response
            .then(response => dispatch(AddGroupCoreInformation(response.data)))
            .then(() => resolve(null)) //Dispatch returns null
            .catch(() => reject("Error contacting server"));
    });
}

export const fetchGeneralInformationToGroupFromServer = (groupId) => (dispatch) => {
    let response = getGroupInfo(groupId)
    response.then(response => dispatch(AddGeneralInformationToGroup({id: groupId, ...response.data})));
}

export const addNewGroup = (groupBody) => (dispatch) =>{
    return new Promise((resolve, reject) => {
        let response = register(groupBody)
        response.then(response => {
            dispatch(AddGroup(response.data));
            return response.data;
        })
            .then((data) => {
                resolve(data.id);
            })
            .catch(()=> reject("Error contacting server, cannot add group"))
    });
}

export const leaveAGroup = (groupId) => (dispatch) => {
    return new Promise((resolve, reject) => {
        let response = leaveGroup(groupId)
        response.then(response => dispatch(RemoveGroup(response.data)))
                .then(() => resolve(null))
                .catch(()=> reject("Error contacting server, cannot leave Group"))
    })
}

export const deleteAGroup = (groupId) => (dispatch) => {
    return new Promise((resolve, reject) => {
        let response = deleteGroup(groupId)
        response.then(response => dispatch(RemoveGroup(response.data)))
                .then(() => resolve(null))
    })
}
