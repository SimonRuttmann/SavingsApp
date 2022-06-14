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
import {showModal} from "./SettingsPopup";


export const NavigationBar = ({getActiveGroupId, setActiveGroupId, groupInformationStore, navToGuestSite, screenSize}) => {


    const dispatch = useDispatch()

    function Redirect() {
        dispatch((logout()))
        keycloak.doLogout()
    }
    const [settings, setSettings] = useState()
    const handleShow = () => setSettings(true);
    const handelClose= () => setSettings(false);



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
                        <Button variant="light" className="buttonStyle maxMarginLeft"  onClick={ () => handleShow()}>
                            Einstellungen
                        </Button>
                        <SettingsPopup show={settings}
                                       onHide={handelClose}
                                       getActiveGroupId={getActiveGroupId}
                                       setActiveGroupId={setActiveGroupId} />
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
                        <Nav>
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
                        </Nav>
                        <Button variant="light"  onClick={ () => handleShow()}>
                            Einstellungen
                        </Button>
                        <SettingsPopup
                                show={settings}
                                onHide={handelClose}
                                getActiveGroupId={getActiveGroupId}
                                setActiveGroupId={setActiveGroupId} />
                        <Button variant="primary" className="buttonStyle" onClick={() => Redirect()}>Logout</Button>
                    </Navbar.Collapse>
                </Container>
            </>
        )
    }
    return (
        <Navbar className="navBar" bg="dark" variant="dark">
            {screenSize>=500?largeScreen():smallScreen()}
        </Navbar>
    )
}