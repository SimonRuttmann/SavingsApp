import {Delete, Get, Post, Put} from "../Axios";
import UserService from "../Auth";

const UserServiceBaseUrl = "http://localhost:8011/userservice/";
const InvitationsBaseUrl = UserServiceBaseUrl + "invitation/";
const GroupBaseUrl = UserServiceBaseUrl + "group";
const UserBaseUrl = UserServiceBaseUrl + "user/";

const token = ()=> UserService.getToken();

//Invitations
export function declineInvitation(groupId){
    return Put(InvitationsBaseUrl + "decline/" + groupId,null,token())
}
export function acceptInvitation(groupId){
    return Put(InvitationsBaseUrl + "accept/" + groupId, null, token())
}
export function invite(body){
    return Post(InvitationsBaseUrl + "invite", body, token())
}
export function receive(){
    return Get(InvitationsBaseUrl + "receive",token())
}

//Groups
export function register(body){
    return Post(GroupBaseUrl + "/" + "register", body, token())
}
export function getGroup(){
    return Get(GroupBaseUrl,token())
}
export function deleteGroup(groupId){
    return Delete(GroupBaseUrl + groupId, token())
}
export function leaveGroup(groupId){
    return Delete(GroupBaseUrl + "leave/" + groupId, token())
}

//Users
export function getUser(userid){
    return Get(UserBaseUrl + userid, token())
}
export function getUsers(){
    return Get(UserBaseUrl + "usernames", token())
}

