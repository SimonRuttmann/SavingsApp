import SockJS from 'sockjs-client';
import {Stomp} from "@stomp/stompjs";
import {subTopic} from "../api/services/Chat";

const socket = new SockJS('http://localhost:8014/ws/chat')
let topic = null;
let stompClient = null;
let isInit = false;
let subscription;

const init = (initialTopic, onMessage) => {
    if(isInit || initialTopic === null) return;
    initializeSocket(initialTopic, onMessage)

}

const connectToSockJs = () => {
    return new Promise( (resolve) => {
        stompClient = Stomp.over(socket)
        stompClient.connect({}, () => resolve())
    })
}

const subscribeToRedis = (topic) => subTopic(topic)

const subscribeWs = (topic, cb) => {
    subscription = stompClient.subscribe(`/sub/chat/rooms/`+topic, (msg) => cb(JSON.parse(msg.body)))
}

const disconnect = () => {
    stompClient.disconnect()
    isInit = false;
}

const initializeSocket = (initialTopic, onMessage) => {
    connectToSockJs().then(() => subscribeWs(initialTopic, onMessage))
    subscribeToRedis(topic).catch(() => console.log("Subscribe to redis did not work"));
}

const changeTopic = (newTopic, onMessage) => {

    if(newTopic === topic) return;

    topic = newTopic;
    subscription
        .unsubscribe()
        .then( () =>  subscribeToRedis(newTopic))
        .then( () => subscribeWs(newTopic, onMessage))
}

const getTopic = () => topic;

/**
 * @param init        Parameters: initialTopic, the topic to subscribe to
 *                                onMessage, callback invoked with the body of the received message
 * @param changeTopic Parameter: newTopic, changes the new topic
 * @param disconnect  Disconnects the socket
 * @param getTopic    Returns the current topic
 * @type {{init: init, disconnect: disconnect, changeTopic: changeTopic, getTopic: (function(): null)}}
 */
export const ChatWebSocket = {
    init,
    changeTopic,
    disconnect,
    getTopic
}
