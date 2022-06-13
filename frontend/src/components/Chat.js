import React, {useEffect, useRef, useState} from 'react'
import SockJS from 'sockjs-client';
import {Stomp} from "@stomp/stompjs";
import {
    Button,
    Card,
    FormControl,
    InputGroup,
    Offcanvas,
} from "react-bootstrap";
import {useSelector} from "react-redux";
import {getMessages, postMessage, subTopic} from "../api/services/Chat";
import {selectUserStore} from "../reduxStore/UserSlice";
import "../css/chat.scss"


function Chat( getActiveGroupId ) {
    const socket = useRef(new SockJS('http://localhost:8014/ws/chat'))
    const chatRef = useRef()
    const topic = useRef(null);
    const stompClient = useRef(null);

    const userStore             = useSelector(selectUserStore);

    //All saved messages
    const [messages, setMessages] = useState([]);
    //New messages to be send
    const [message, setMessage] = useState();
    //Topic equals selected groupID

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);

    const handleShow = () => setShow(true);
    useEffect( () => {

        if(getActiveGroupId.getActiveGroupId === topic.current || getActiveGroupId.getActiveGroupId == null) return;
        console.log("INIT topic",topic.current)
        console.log("INIT getActiveGroupID ",getActiveGroupId.getActiveGroupId)
        topic.current = getActiveGroupId.getActiveGroupId;
        connectToSockJs()
        subscribeToTopic()
        getMessagesForTopic()

        // eslint-disable-next-line react-hooks/exhaustive-deps
    },[getActiveGroupId.getActiveGroupId,topic])

    const connectToSockJs = () => {
        console.log("Trying to connect ",stompClient)
        if(stompClient.current != null) disconnect()
        stompClient.current = Stomp.over(socket.current)

        stompClient.current.connect({}, (frame) => {
            //console.log("Connected: ",frame)
            subscribeToGroup()
        })
    }
        if(stompClient !=null && stompClient.current != null)
            stompClient.current.onDisconnect = async (receipt) => {
            console.warn("Has been disconnected");
        };
        if(stompClient !=null && stompClient.current != null)
            stompClient.current.onWebSocketClose = async (evt) => {
            console.warn("Websocket has been closed");
        };
    //Handles the subscribe to stomp socket
    const subscribeToGroup = () => {
        let testindex = 1;
        stompClient.current.subscribe(`/sub/chat/rooms/`+topic.current, (msg) => {
            console.log("I am run ",testindex++)
            const json =JSON.parse(msg.body)
            console.log("Message State before: ",messages)
            console.log("Received message: ",json)
            addMessageToState(json)
            console.log("New messages ",messages)
        })
    }

    const addMessageToState = (_messages) =>{
        console.log("Wants to add following messages to state ",_messages)
        setMessages(prevState => {
            return [
                ...prevState,
                _messages
            ]
        })
    }

    //Tells the backend to establish a connection to redis sub/pub
    const subscribeToTopic = () => {
        subTopic(topic.current)
    }

    const getMessagesForTopic = () => {
        console.log("Started fetching with topic: ",topic.current)
        getMessages(topic.current).then((tmp)=> {
            setMessages(tmp.data)
        })
    }

    const disconnect = () => {
        stompClient.current.disconnect()
    }

    function sendMessage(){
        const body = {
            content: chatRef.current.value,
            sender : userStore.username,
            topic : topic.current
        }
        console.log("Message to send ",body)
        postMessage(body)
    }

    function generateShownMessages(){
        let messageForRender = []
        //console.log("Messages to be shown ",messages)
        let index = 0;
        for (let msg of messages){
            if(msg.sender === userStore.username){
                messageForRender.push(
                    <Card key={msg.sender+" "+msg.content+" "+index++} className="myMessageDisplay">
                        <Card.Title>{msg.sender}</Card.Title>
                        <Card.Body>{msg.content}</Card.Body>
                    </Card>
                )
            }
            else {
                messageForRender.push(
                    <Card key={msg.sender+" "+msg.content+" "+index++} className="otherMessageDisplay">
                        <Card.Title>{msg.sender}</Card.Title>
                        <Card.Body>{msg.content}</Card.Body>
                    </Card>
                )
            }
        }
        return messageForRender
    }

  return (
      <>
          <Button className="chatButton" variant="dark" onClick={()=>handleShow()}>
              Chat
          </Button>
          <Offcanvas show={show} onHide={handleClose}>
              <Offcanvas.Header closeButton>
                  <Offcanvas.Title>Chat</Offcanvas.Title>
              </Offcanvas.Header>
              <Offcanvas.Body>
                  {generateShownMessages()}
              </Offcanvas.Body>
              <InputGroup className="mb-3">
                  <FormControl
                      ref={chatRef}
                      value={message} onChange={(e) => {setMessage(e.target.value)}}
                      placeholder="Gebe deine Nachricht ein"
                      aria-label="message"
                      aria-describedby="message"
                  />
                  <Button variant="outline-success" id="button-addon2" onClick={sendMessage}>
                      Send
                  </Button>
              </InputGroup>
          </Offcanvas>
      </>
    )
}

export default Chat