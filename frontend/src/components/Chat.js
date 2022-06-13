import React, {useEffect, useState} from 'react'
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
    const userStore             = useSelector(selectUserStore);

    //All saved messages
    const [messages, setMessages] = useState([]);
    //New messages to be send
    const [message, setMessage] = useState();
    //Topic equals selected groupID
    const [topic, setTopic] = useState();
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);

    const handleShow = () => {
        if(getActiveGroupId.getActiveGroupId != null) {
            setTopic(getActiveGroupId.getActiveGroupId)
            console.log("Changing topic to ",topic)
        }
        else console.log("Its still null")
        setShow(true);
    }
    let stompClient = null;

    useEffect(()=>{
        if(getActiveGroupId.getActiveGroupId != null)
            setTopic(getActiveGroupId.getActiveGroupId)
        console.log("Chat changed topic to ",topic)

        connectToSockJs()
        subscribeToTopic()
        getMessagesForTopic()
    },[getActiveGroupId.getActiveGroupId,topic])

    const connectToSockJs = () => {
        if(stompClient != null)disconnect()
        const socket = new SockJS('http://localhost:8014/ws/chat')
        stompClient = Stomp.over(socket)
        stompClient.connect({}, (frame) => {
            //console.log("Connected: ",frame)
            subscribeToGroup()
        })
    }

    //Handles the subscribe to stomp socket
    const subscribeToGroup = () => {
        stompClient.subscribe(`/sub/chat/rooms/`+topic, (msg) => {
            const json =JSON.parse(msg.body)
            console.log("Message State before: ",messages)
            console.log("Received message: ",json)
            setMessages(messages.concat(json))
            console.log("New messages ",messages)
        })
    }

    //Tells the backend to establish a connection to redis sub/pub
    const subscribeToTopic = () => {
        subTopic(topic)
    }

    const getMessagesForTopic = () => {
        console.log("Started fetching with topic: ",topic)
        getMessages(topic).then((tmp)=> {
            setMessages(tmp.data)
        })
    }

    const disconnect = () => {
        stompClient.disconnect()
    }

    function sendMessage(){
        const body = {
            content: message,
            sender : userStore.username,
            topic : topic
        }
        console.log("Message to send ",body)
        postMessage(body)
    }

    function generateShownMessages(){
        let messageForRender = []
        console.log("Messages to be shown ",messages)
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