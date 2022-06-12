import {Get,Post} from "../Axios";

const ChatServiceBaseUrl = "http://localhost:8014/chat/";

function unSubTopic(topic){
    return Get(ChatServiceBaseUrl + "unsub/" + topic)
}

function subTopic(topic){
    return Get(ChatServiceBaseUrl + "sub/" + topic)
}

function getMessages(topic){
    return Get(ChatServiceBaseUrl + "rooms/" + topic + "/messages")
}

function postMessage(body){
    return Post(ChatServiceBaseUrl + "message",body)
}

export {subTopic,getMessages,postMessage,unSubTopic}