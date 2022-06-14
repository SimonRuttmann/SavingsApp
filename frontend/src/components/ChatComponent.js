import React, {useEffect, useRef, useState} from 'react'
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
import {useSubscription} from "react-stomp-hooks";

function ChatComponent(getActiveGroupId ) {
    const chatRef = useRef()
    const userStore = useSelector(selectUserStore);

    //All saved messages
    const [messages, setMessages] = useState([]);
    const [show, setShow] = useState(false);
    const topic = useRef(null)

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    useEffect( () => {
        if(getActiveGroupId.getActiveGroupId == null || topic === getActiveGroupId.getActiveGroupId) return;
        topic.current = (getActiveGroupId.getActiveGroupId);
        subTopic(topic.current)
        getMessagesForTopic()
    },[getActiveGroupId.getActiveGroupId])

    useSubscription(['/sub/chat/rooms/'+topic.current],(msg)=> addMessageToState(msg))

    const addMessageToState = (_message) =>{
        _message = JSON.parse(_message.body)
        console.log("Wants to add following messages to state ",_message)
        setMessages(prevState => {
            return [
                ...prevState,
                _message
            ]
        })
    }

    const getMessagesForTopic = () => {
        console.log("Started fetching with topic: ",topic.current)
        getMessages(topic.current).then((messageArray)=> {
            setMessages(messageArray.data.reverse())
        })
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
          <Offcanvas  show={show} onHide={handleClose}>
              <Offcanvas.Header closeButton>
                  <Offcanvas.Title>Chat</Offcanvas.Title>
              </Offcanvas.Header>
              <Offcanvas.Body>
                  {generateShownMessages()}
              </Offcanvas.Body>
              <InputGroup className="mb-3">
                  <FormControl
                      ref={chatRef}
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

export default ChatComponent