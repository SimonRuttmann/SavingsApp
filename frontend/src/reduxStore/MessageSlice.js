
import {createSlice} from '@reduxjs/toolkit'

export const MessageSlice = createSlice({
    name: "messaging",
    initialState: {value: {topic: "Test", messages: []}},
    reducers: {
        changeTopicOfMessages: (state, action) => {
            state.value = action.payload
            console.log(state.value)
        },
        updateMessages: (state, action) => {
            state.value = {
                id: "",
                email: "",
                username: ""
            };
        }
    }
});

export const {changeTopicOfMessages, updateMessages} = MessageSlice.actions

export default MessageSlice.reducer

