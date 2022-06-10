import { configureStore} from "@reduxjs/toolkit";
import categorySlice from "./CategorySlice";
import savingEntrySlice from "./SavingEntrySlice";
import groupInformationSlice from "./GroupInformationSlice";
import userSlice from "./UserSlice";
import advertisementSlice from "./AdvertisementSlice";


export const store = configureStore({
    reducer: {
        category: categorySlice,
        savingEntry: savingEntrySlice,
        groupInformation: groupInformationSlice,
        user: userSlice,
        advertisement: advertisementSlice
    }
})

export default store;