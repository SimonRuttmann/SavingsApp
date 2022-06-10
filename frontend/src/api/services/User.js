import {Delete, Get, Post, Put} from "../Axios";

const UserServiceBaseUrl = "http://localhost:8011/userservice/";
const InvitationsBaseUrl = UserServiceBaseUrl + "invitation/";
const GroupBaseUrl = UserServiceBaseUrl + "group/";
const UserBaseUrl = UserServiceBaseUrl + "user/";


//Invitations
export function declineInvitation(groupId, header){
    return Put(InvitationsBaseUrl + "decline/" + groupId,null,header)
}
export function acceptInvitation(groupId, header){
    return Put(InvitationsBaseUrl + "accept/" + groupId, null, header)
}
export function invite(body, header){
    return Post(InvitationsBaseUrl + "invite", body, header)
}
export function receive(header){
    return Get(InvitationsBaseUrl + "receive",header)
}

//Groups
export function register(body, header){
    return Post(GroupBaseUrl + "register", body, header)
}
export function getGroup(header){
    return Get(GroupBaseUrl,header)

}
export function deleteGroup(groupId, header){
    return Delete(GroupBaseUrl + groupId, header)

}
export function leaveGroup(groupId, header){
    return Delete(GroupBaseUrl + "leave/" + groupId, header)
}

//Users
export function getUser(userid, header){
    return Get(UserBaseUrl + userid, header)
}
export function getUsers(header){
    return Get(UserBaseUrl + "usernames", header)
}

