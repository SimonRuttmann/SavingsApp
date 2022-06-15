import ChatComponent from "../ChatComponent";
import SettingsPopup from "./SettingsPopup";
import React, {useState} from "react";
import "../../css/styles.scss"
import "../../css/homepage.scss"
import {Button, Container, Nav, Navbar, NavDropdown} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import keycloak from '../../api/Auth'
import {logout} from "../../reduxStore/UserSlice";
import {useDispatch} from "react-redux";
import {StompSessionProvider} from "react-stomp-hooks";

export const NavigationBar = ({getActiveGroupId, setActiveGroupId, groupInformationStore, navToGuestSite, screenSize}) => {


    const dispatch = useDispatch()

    function Redirect() {
        dispatch((logout()))
        keycloak.doLogout()
    }
    const [settings, setSettings] = useState(false)
    const handleShow = () => {setSettings(true); console.log("---popup öffnen");}
    const handelClose= () => {setSettings(false);console.log("---popup schließen");}

    if(settings == true){
        console.log("PopUp should be open: state:"+settings)
    } else{
        console.log("PopUp should be closed: state:"+settings)
    }
    console.log("selected group in Nav: "+getActiveGroupId)

   /* if(settings == false){
        setSettings(null)
    }*/

    function SettingsButton(){
        console.log("Buttonclick")
        if(settings == false){
            handleShow()
        }
        if(settings == true){
            console.log("trick")
            setSettings(null)
            let groupName = document.getElementById("SettingsLittle")
            //groupName.hidden

        }
    }


    function smallScreen(){
        return(
            <>
                <Container>
                    <Navbar.Toggle/>
                    <Navbar.Collapse className="justify-content-end">
                        <Nav>
                            <StompSessionProvider url={"http://localhost:8014/ws/chat"}>
                                <ChatComponent getActiveGroupId = {getActiveGroupId}/>
                            </StompSessionProvider>
                        </Nav>
                        <div className="showSelectedGroup">

                            {getActiveGroupId != null ?
                                groupInformationStore.find(group => group.id === getActiveGroupId).groupName : null}
                        </div>
                        <Button variant="primary" className="buttonStyle" onClick={() => Redirect()}>Logout</Button>
                    </Navbar.Collapse>
                </Container>
                <Container>
                    <Nav className="fullWidth">
                        {Array.isArray(groupInformationStore) && groupInformationStore.length > 0 ?
                            <NavDropdown title="Ansicht" id="basic-nav-dropdown">
                                { groupInformationStore.map(group =>
                                    <NavDropdown.Item key={`Group-${group.id}`}
                                                      onClick={(e) => {
                                                          e.preventDefault()
                                                          setActiveGroupId(group.id)
                                                      }}
                                    >{group.groupName}
                                    </NavDropdown.Item>)}
                            </NavDropdown>: null}
                        <Button variant="light" className="buttonStyle maxMarginLeft"  onClick={ () =>handleShow()}>
                            Einstellungen
                        </Button>

                    </Nav>
                </Container>
            </>
        )
    }
    function largeScreen(){
        return(
            <>
                <Container>
                    <Navbar.Toggle/>
                    <Navbar.Collapse className="justify-content-end">
                        <Nav className="fullWidth">
                            {Array.isArray(groupInformationStore) && groupInformationStore.length > 0 ?
                                <NavDropdown title="Ansicht" id="basic-nav-dropdown">
                                    { groupInformationStore.map(group =>
                                        <NavDropdown.Item key={`Group-${group.id}`}
                                                          onClick={(e) => {
                                                              e.preventDefault();
                                                              setActiveGroupId(group.id);
                                                          }}
                                        >{group.groupName}
                                        </NavDropdown.Item>)}
                                </NavDropdown>: null}
                            <StompSessionProvider url={"http://localhost:8014/ws/chat"}>
                                <ChatComponent getActiveGroupId = {getActiveGroupId}/>
                            </StompSessionProvider>
                            <div className="showSelectedGroup">
                            {getActiveGroupId != null ?
                                groupInformationStore.find(group => group.id === getActiveGroupId).groupName : null}
                            </div>
                        <Button variant="light"  onClick={ () => handleShow()}>
                            Einstellungen
                        </Button>
                        <Button variant="primary" className="buttonStyle" onClick={() => Redirect()}>Logout</Button>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </>
        )
    }
    return (
        <>
        <Navbar className="navBar" bg="dark" variant="dark">
            {screenSize>=500?largeScreen():smallScreen()}
        </Navbar>
        <SettingsPopup id="SettingsLittle" show={settings}
                       onHide={handelClose}
                       getActiveGroupId={getActiveGroupId}
                       setActiveGroupId={setActiveGroupId} />
        </>
    )
}