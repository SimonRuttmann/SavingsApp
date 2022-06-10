import {Get,Post,Put,Delete} from "../Axios";

const ContentServiceBasePath = "http://localhost:8012/content/";
const savingPath = ContentServiceBasePath + "savingEntry/";
const categoryPath = ContentServiceBasePath + "category/";
const processingPath = ContentServiceBasePath + "processing/";

//Saving
export function getAllGroupEntrys(groupId, header){
    return Get(savingPath + groupId, header)
}
export function getGroupEntry(groupId, savingEntryId,header){
    return Get(savingPath + groupId + "/" + savingEntryId, header)
}
export function addGroupEntry(groupId, body, header){
    return Post(savingPath + groupId, body, header)
}
export function updateGroupEntry(groupId, header){
    return Put(savingPath + groupId, header)
}
export function deleteGroupEntry(groupId, savingEntryId,header){
    return Delete(savingPath + groupId + "/" + savingEntryId, header)
}

//Categorys
export function getAllCategorys(groupId, header){
    return Get(categoryPath + groupId, header)
}
export function getCategory(groupId, categoryId, header){
    return Get(categoryPath + groupId + "/" + categoryId, header)
}
export function addCategory(groupId, body, header){
    return Post(categoryPath + groupId, body, header)
}
export function updateCategory(groupId, header){
    return Put(categoryPath + groupId, header)
}
export function deleteCategory(groupId, categoryId, header){
    return Delete(categoryPath + groupId + "/" + categoryId, header)
}

//Processing
export function getGroupInfo(groupId, header){
    return Get(categoryPath + groupId, header)
}
export function filterEntrys(groupId, body, header){
    return Post(categoryPath + groupId, body, header)
}