import {createSlice} from '@reduxjs/toolkit'

export const inflationSlice = createSlice({
    name: "inflation",
    initialState: {value: 0.48},
    reducers: {
        updateInflationRate: (state, action) => {
            state.value = action.payload
            console.log('Inflationrate: ' + state.value)
        },
    }
});

export const {updateInflationRate} = inflationSlice.actions

export default inflationSlice.reducer