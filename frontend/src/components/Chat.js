import React, {useEffect, useState} from 'react'
import SockJS from 'sockjs-client';
import {Stomp} from "@stomp/stompjs";
import {
    Button,
    Card,
    CardGroup,
    Form, FormControl,
    InputGroup,
    Offcanvas,
} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import {getMessages, postMessage, subTopic, unSubTopic} from "../api/services/Chat";
import {selectUserStore} from "../reduxStore/UserSlice";
import "../css/chat.scss"


function Chat( getActiveGroupId ) {
    const userStore             = useSelector(selectUserStore);

    //All saved messages
    const [messages, setMessages] = useState([]);
    //New messages to be send
    const [message, setMessage] = useState();
    //Topic equals selected groupID
    const [topic, setTopic] = useState('Test');
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    let stompClient = null;

    useEffect(()=>{
        console.log("Change Topic to",getActiveGroupId.getActiveGroupId)
        setTopic(getActiveGroupId.getActiveGroupId)
        connectToSockJs()
        subscribeToTopic()
        getMessagesForTopic()
    },[])

    const connectToSockJs = () => {
        const socket = new SockJS('http://localhost:8014/ws/chat')
        stompClient = Stomp.over(socket)
        stompClient.connect({}, (frame) => {
            console.log("Connected: ",frame)
            subscribeToGroup()
        })
    }

    //Handles the subscribe to stomp socket
    const subscribeToGroup = () => {
        stompClient.subscribe(`/sub/chat/rooms/`+topic, (msg) => {
            setMessages(messages.concat(msg.body))
        })
    }

    //Tells the backend to establish a connection to redis sub/pub
    const subscribeToTopic = () => {
        subTopic(topic)
    }

    const getMessagesForTopic = () => {
        getMessages(topic).then((tmp)=> {
            setMessages(messages.concat(tmp.data))
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
        for (let msg of messages){
            if(msg.sender === userStore.username){
                messageForRender.push(
                    <Card  className="myMessageDisplay">
                        <Card.Title>{msg.sender}</Card.Title>
                        <Card.Body>{msg.content}</Card.Body>
                    </Card>
                )
            }
            else {
                messageForRender.push(
                    <Card className="otherMessageDisplay">
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
          <Button variant="dark" onClick={
              () => {
                  handleShow()
              }
          }>
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