import {createSlice} from '@reduxjs/toolkit'

export const advertismentSlice = createSlice({
    name: "advertisment",
    initialState: {value: {statistic1: 0, statistic2: 0, statistic3: 0}},
    reducers: {
        updateAdvertismentData: (state, action) => {
            state.value = action.payload
            console.log('Advertismentdata: ' + state.value)
        },
    }
});

export const {updateAdvertismentData} = advertismentSlice.actions

export default advertismentSlice.reducer