import {Get,Post} from "../Axios";

const ChatServiceBaseUrl = "http://localhost:8014/chat/";

function subTopic(topic){
    return Get(ChatServiceBaseUrl + "sub/" + topic)
}

function getMessages(topic){
    return Get(ChatServiceBaseUrl + "rooms/" + topic + "/messages")
}

function postMessage(message){
    return Post(ChatServiceBaseUrl + "message",message)
}

export {subTopic,getMessages,postMessage}