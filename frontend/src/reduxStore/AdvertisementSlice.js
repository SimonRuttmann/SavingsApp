import {createSlice} from '@reduxjs/toolkit'

export const AdvertisementSlice = createSlice({
    name: "advertisment",
    initialState: {value: {statistic1: {}, statistic2: {}, statistic3: {}}},
    reducers: {
        updateAdvertisementData: (state, action) => {
            state.value = action.payload
        },
    }
});

export const {updateAdvertisementData} = AdvertisementSlice.actions

export default AdvertisementSlice.reducer

export const selectAdvertisementStore = (state) => state.advertisement;