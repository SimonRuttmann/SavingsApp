import {Get, Post, Put} from "../Axios";

const UserServiceBaseUrl = "http://localhost:8011/userservice/";
const InvitationsBaseUrl = UserServiceBaseUrl + "invitation/";
const GroupBaseUrl = UserServiceBaseUrl + "group/";
const UserBaseUrl = UserServiceBaseUrl + "user/";


//Invitations
export function declineInvitation(groupId){
    return Put(InvitationsBaseUrl + "decline/" + groupId)
}
export function acceptInvitation(groupId){
    return Put(InvitationsBaseUrl + "accept/" + groupId)

}
export function invite(body){
    return Post(InvitationsBaseUrl + "invite", body)

}
export function receive(header){
    return Get(InvitationsBaseUrl + "receive")
}

//Groups
export function register(body){
    return Post(GroupBaseUrl + "register", body)
}
export function getGroup(headers){
    return Get(GroupBaseUrl,headers)

}
export function deleteGroup(groupId){
    return Delete(GroupBaseUrl + groupId)

}
export function leaveGroup(groupId){
    return Delete(GroupBaseUrl + "leave/" + groupId)
}

//Users
export function getUser(userid,header){
    return Get(UserBaseUrl + userid)
}
export function getUsers(header){
    return Get(UserBaseUrl + "usernames",headers)

}

