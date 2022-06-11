import { configureStore} from "@reduxjs/toolkit";
import categorySlice from "./CategorySlice";
import groupInformationSlice from "./GroupInformationSlice";
import userSlice from "./UserSlice";
import advertisementSlice from "./AdvertisementSlice";
import messageSlice from "./MessageSlice";
import contentSlice from "./ContentSlice";


export const store = configureStore({
    reducer: {
        category: categorySlice,
        groupInformation: groupInformationSlice,
        user: userSlice,
        advertisement: advertisementSlice,
        message: messageSlice,
        content: contentSlice
    }
})

export default store;