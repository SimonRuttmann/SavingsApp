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
    const [displayedMessages, setDisplayedMessages] = useState([])
    const [messages, setMessages] = useState([]);
    const [show, setShow] = useState(false);
    const topic = useRef(null)
    const offcanvasRef = useRef(null)

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
        setMessages(prevState => {
            return [
                ...prevState,
                _message
            ]
        })
    }

    const getMessagesForTopic = () => {
        getMessages(topic.current).then((messageArray)=> {
            setMessages(messageArray.data.reverse())
        })
    }

    function sendMessage(){
        const chatRefValue = chatRef.current.value;


        if(chatRefValue === "" || chatRefValue.trim() === "") return

        const body = {
            content: chatRefValue,
            sender : userStore.username,
            topic : topic.current
        }
        chatRef.current.value = "";

        postMessage(body)
    }

    const fontFamily = "Segoe UI"
    const fontSize = "16"
    const textFont = `${fontSize}pt ${fontFamily}`

    function displayMessage(msg){
        let placeToDisplayStuff
        if(offcanvasRef.current == null || offcanvasRef.current.clientWidth == null) placeToDisplayStuff = 300;
        else placeToDisplayStuff = offcanvasRef.current.clientWidth ;

        let stringToDisplay = "";
        for( let string of msg.split(" ")){
            let changeableString = string;
            let slice = changeableString
            let maxSingleStringLength = 32;

            while ( getTextWidth(slice,textFont) > placeToDisplayStuff){
                slice = changeableString.slice(0,maxSingleStringLength) + " ";
                if( getTextWidth(slice,textFont) > placeToDisplayStuff){
                    console.log("We entered")
                    maxSingleStringLength--;
                }
                else{
                    stringToDisplay += slice + " ";
                    changeableString = changeableString.slice(slice.length,changeableString.length)
                }
                console.log("Changable String",changeableString)
                console.log("Slice ",slice)
            }
            stringToDisplay += slice + " ";
        }
        console.log(stringToDisplay)
        return stringToDisplay
    }

    function getTextWidth(text, font) {

        let canvas = getTextWidth.canvas || (getTextWidth.canvas = document.createElement("canvas"));

        let context = canvas.getContext("2d");
        context.font = font;

        return context.measureText(text).width;
    }

    function generateShownMessages(){
        let messageForRender = []
        let index = 0;
        for (let msg of messages){
            if(msg.sender === userStore.username){
                messageForRender.push(
                    <Card key={msg.sender+" "+msg.content+" "+index++} className="myMessageDisplay">
                        <Card.Title>{msg.sender}</Card.Title>
                        <Card.Body>{displayMessage(msg.content)}</Card.Body>
                    </Card>
                )
            }
            else {
                messageForRender.push(
                    <Card key={msg.sender+" "+msg.content+" "+index++} className="otherMessageDisplay">
                        <Card.Title>{msg.sender}</Card.Title>
                        <Card.Body>{displayMessage(msg.content)}</Card.Body>
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
              <Offcanvas.Body ref={offcanvasRef}>
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