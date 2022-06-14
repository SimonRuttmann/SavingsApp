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

export const NavigationBar = ({newLoad,getActiveGroupId, setActiveGroupId, groupInformationStore, navToGuestSite}) => {

    const dispatch = useDispatch()

    function Redirect() {
        dispatch((logout()))
        keycloak.doLogout()
    }
    const [settings, setSettings] = useState(false)
    const handleShow = (set) => setSettings(set);
    //const handleHide = (set) => setSettings(set);

    if(settings == true ){
        console.log("NAVrereder: true")
        //setTest(showSettings)
    } else {
        console.log("Navrereder: false")
        //setTest(showSettings)
    }

    return (
        <Navbar bg="dark" variant="dark">
        <Container>
            <Navbar.Brand>Haushalt</Navbar.Brand>
            <Navbar.Toggle/>
            <Navbar.Collapse className="justify-content-end">
                <Nav className="me-auto">
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
                    <StompSessionProvider url={"http://localhost:8014/ws/chat"}>
                        <ChatComponent getActiveGroupId = {getActiveGroupId}/>
                    </StompSessionProvider>
                </Nav>
                <p className="showSelectedGroup">

                    {getActiveGroupId != null ?
                        groupInformationStore.find(group => group.id === getActiveGroupId).groupName : null}
                </p>
                <Button variant="light"  onClick={ () => handleShow(true)}>
                    Einstellungen
                </Button>
                <SettingsPopup newLoad={newLoad} setShowSettings={setSettings} handelShow={handleShow}  getActiveGroupId={getActiveGroupId} setActiveGroupId={setActiveGroupId} settings={settings}/>
                <Button variant="primary" className="buttonStyle" onClick={() => Redirect()}>Logout</Button>
            </Navbar.Collapse>
        </Container>
        </Navbar>
    )
}