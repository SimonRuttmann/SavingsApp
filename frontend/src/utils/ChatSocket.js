import React, {useEffect, useRef, useState} from 'react'
import SockJS from 'sockjs-client';
import {Stomp} from "@stomp/stompjs";
import {
    Card,
} from "react-bootstrap";
import {useSelector} from "react-redux";
import {getMessages, postMessage, subTopic} from "../api/services/Chat";
import {selectUserStore} from "../reduxStore/UserSlice";
import "../css/chat.scss"

    const socket = new SockJS('http://localhost:8014/ws/chat')
    let topic = null;
    let stompClient = null;
    let isInit = false;

    export const ChatWebSocket = {
        init,changeTopic,onMessageReceived
    }

    const init = () =>
    {
        if(isInit) return;
        connectToSockJs()
    }

    

const connectToSockJs = () => {
    console.log("Trying to connect ",stompClient)
    stompClient = Stomp.over(socket)

    stompClient.connect({})
}
