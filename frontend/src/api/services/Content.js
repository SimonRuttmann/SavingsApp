import {Get,Post,Put,Delete} from "../Axios";
import UserService from "../Auth";

const ContentServiceBasePath = "http://localhost:8012/content/";
const savingPath = ContentServiceBasePath + "savingEntry/";
const categoryPath = ContentServiceBasePath + "category/";
const processingPath = ContentServiceBasePath + "processing/";

const token = ()=> UserService.getToken();

//Saving
export function getAllGroupEntries(groupId){
    return Get(savingPath + groupId, token())
}
export function getGroupEntry(groupId, savingEntryId){
    return Get(savingPath + groupId + "/" + savingEntryId, token())
}
export function addGroupEntry(groupId, body){
    return Post(savingPath + groupId, body, token())
}
export function updateGroupEntry(groupId){
    return Put(savingPath + groupId, token())
}
export function deleteGroupEntry(groupId, savingEntryId){
    return Delete(savingPath + groupId + "/" + savingEntryId, token())
}

//Categorys
export function getAllCategorys(groupId){
    return Get(categoryPath + groupId, token())
}
export function getCategory(groupId, categoryId){
    return Get(categoryPath + groupId + "/" + categoryId, token())
}
export function addCategory(groupId, body){
    return Post(categoryPath + groupId, body, token())
}
export function updateCategory(groupId, body){
    return Put(categoryPath + groupId, body, token())
}
export function deleteCategory(groupId, categoryId){
    return Delete(categoryPath + groupId + "/" + categoryId, token())
}

//Processing
export function getGroupInfo(groupId){
    return Get(processingPath + groupId, token())
}
export function getSavingEntriesProcessed(groupId, body){
    return Post(processingPath + groupId, body, token())
}