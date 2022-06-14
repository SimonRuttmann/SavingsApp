import { configureStore} from "@reduxjs/toolkit";
import categorySlice from "./CategorySlice";
import groupInformationSlice from "./GroupInformationSlice";
import userSlice from "./UserSlice";
import contentSlice from "./ContentSlice";


export const store = configureStore({
    reducer: {
        category: categorySlice,
        groupInformation: groupInformationSlice,
        user: userSlice,
        content: contentSlice
    }
})

export default store;