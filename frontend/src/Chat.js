import React, {useEffect, useState} from 'react'
import SockJS from 'sockjs-client';
import {Stomp} from "@stomp/stompjs";
import {
    Badge,
    Button,
    Card,
    CardGroup,
    Form, FormControl,
    InputGroup,
    NavDropdown,
    Offcanvas,
    OverlayTrigger,
    Popover
} from "react-bootstrap";

function Chat( user ) {
    const [messages, setMessages] = useState([]);
    const [message, setMessage] = useState([]);
    const [topic, setTopic] = useState('NEU2');
    const [groupId, setGroupId] = useState('Robin Röcker');
    const [newMessage, setNewMessage] = useState('');
    const [show, setShow] = useState(false);
    const [data , setData] = useState([])
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const socket = new SockJS('http:localhost:8080/ws/chat')
    const stompClient = Stomp.over(socket)

    const startup = () => {
        connectToSockJs()
        subscribeToTopic()
        getMessagesForTopic()
    }

    const connect = () => {

        stompClient.connect({}, (frame) => {
            console.log('connected: ' + frame)
            stompClient.subscribe(`/sub/chat/rooms/${groupId}`, (message) => {
                console.log(message)
            })
            fetch('http://localhost:8080/chat/sub/TEST123')
            fetch('http://localhost:8080/chat/rooms/TEST123/messages')
                .then(response => response.json())
                .then(data => console.log(data))

        })
    }
    const connectToSockJs = () => {
        stompClient.connect({}, (frame) => {
            console.log('connected: ' + frame)
            subscribeToGroup()
        })
    }

    const subscribeToGroup = () => {
        stompClient.subscribe(`/sub/chat/rooms/${groupId}`, (message) => {
            console.log(message)
        })
    }

    const subscribeToTopic = () => {
        fetch(`http://localhost:8080/chat/sub/${topic}`)
    }

    const unsubscribeToTopic = () => {
        fetch(`http://localhost:8080/chat/unsub/${topic}`)
    }

    const getMessagesForTopic = () => {
        fetch(`http://localhost:8080/chat/rooms/${topic}/messages`)
            .then(response => response.json())
            .then(data => setData(data))
    }

    const sendMessage = () => {
        fetch('http://localhost:8080/chat/message',{
            body: {
                'content': message,
                'sender' : user,
                'topic' : topic
            }})
    }

    const disconnect = () => {
        stompClient.disconnect()
    }

    const popover = (
        <Popover id='popover-basic'>
            <Popover.Header as='h3'>Chat</Popover.Header>
            <Popover.Body>
                <CardGroup>
                    <Card>
                        <Form>
                            <label>Message :
                                <input type='text'/>
                            </label>
                            <Button>Send</Button>
                        </Form>
                    </Card>
                </CardGroup>
            </Popover.Body>
        </Popover>
    )

  return (
/*      <OverlayTrigger trigger='click' placement='right' overlay={popover}>
          <Badge pill bg="success" onClick={() => {
              connectToSockJs()
              subscribeToTopic()
              getMessagesForTopic()
          }}>
              Chat
          </Badge>
      </OverlayTrigger>*/
      <>
          <Button variant="dark" onClick={
              () => {
                  handleShow()
                  startup()

              }
          }>
              Chat
          </Button>
          <Offcanvas show={show} onHide={handleClose}>
              <Offcanvas.Header closeButton>
                  <Offcanvas.Title>Chat</Offcanvas.Title>
              </Offcanvas.Header>
              <Offcanvas.Body>
                  {data.map(m =>
                      <>
                      <CardGroup>
                      <Card>
                          <Card.Title>{m.sender}</Card.Title>
                          <Card.Body>{m.content}</Card.Body>
                      </Card>
                      </CardGroup>
                      <br/>
                      </>
                      )}
              </Offcanvas.Body>
              <InputGroup className="mb-3">
                  <FormControl value={newMessage} onChange={(e) => {setNewMessage(e.target.value)}}
                      placeholder="Gebe deine Nachricht ein"
                      aria-label="message"
                      aria-describedby="message"
                  />
                  <Button variant="outline-success" id="button-addon2" onClick={() => {                  setData([...data, {
                      "content": newMessage,
                      "sender": "Robin Röcker",
                      "topic": "NEU2"
                  }])}}>
                      Send
                  </Button>
              </InputGroup>
          </Offcanvas>
      </>
    )
}

export default Chat