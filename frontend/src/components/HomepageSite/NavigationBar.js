import Chat from "../Chat";
import SettingsPopup from "./SettingsPopup";
import React from "react";
import "../../css/styles.scss"
import "../../css/homepage.scss"
import {Button, Container, Nav, Navbar, NavDropdown} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import keycloak from '../../api/Auth'
import {logout} from "../../reduxStore/UserSlice";
import {useDispatch} from "react-redux";

export const NavigationBar = ({getActiveGroupId, setActiveGroupId, groupInformationStore, navToGuestSite}) => {

    const dispatch = useDispatch()

    function Redirect() {
        dispatch((logout()))
        keycloak.doLogout()
    }
    console.log("NAVIGATIONBAR")
    console.log(groupInformationStore)

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
                       <Chat getActiveGroupId = {getActiveGroupId}/>
                </Nav>
                <p className="showSelectedGroup">

                    {getActiveGroupId != null ?
                        groupInformationStore.find(group => group.id === getActiveGroupId).groupName : null}
                </p>

                <SettingsPopup getActiveGroupId={getActiveGroupId}/>
                <Button variant="primary" className="buttonStyle" onClick={() => Redirect()}>Logout</Button>
            </Navbar.Collapse>
        </Container>
        </Navbar>
    )
}