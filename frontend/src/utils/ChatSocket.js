import SockJS from 'sockjs-client';
import {Stomp} from "@stomp/stompjs";
import {subTopic} from "../api/services/Chat";

const _socket = new SockJS('http://localhost:8014/ws/chat')

let _stompClient = null;
let _isInit = false;
let _subscription;

let _onMessage = null;
let _topic = null;

const init = (initialTopic, onMessage) => {
    if(_isInit || initialTopic == null) return;
    _onMessage = onMessage;
    initializeSocket(initialTopic)
}

const connectToSockJs = () => {
    _stompClient = Stomp.over(_socket)
    _stompClient.connect({}, () => subscribeWs());
}

const subscribeToRedis = () => subTopic(_topic)

const subscribeWs = () => {
    _subscription = _stompClient.subscribe(`/sub/chat/rooms/`+_topic, (msg) => _onMessage(JSON.parse(msg.body)))
}

const disconnect = () => {
    _stompClient.disconnect()
    _isInit = false;
}

const initializeSocket = () => {
    connectToSockJs();
    subscribeToRedis().catch(() => console.log("Subscribe to redis did not work"));
}

const changeTopic = (newTopic) => {

    if(newTopic === _topic) return;

    _topic = newTopic;

    if(_subscription != null) {
        _subscription
            .unsubscribe()
            .then(() => subscribeToRedis())
            .then(() => subscribeWs())
        return;
    }

    subscribeToRedis().catch(() => console.log("Subscribe to redis did not work"));
    subscribeWs()
}

const getTopic = () => _topic;

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
