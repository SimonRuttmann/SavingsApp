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

export const NavigationBar = ({getActiveGroupId, groups, realGroups, setSelectedGroup, selectedGroup, setSelectedSettingsGroup, selectedSettingsGroup, AddGroup, DeleteGroup, navToGuestSite}) => {
    const dispatch = useDispatch()
    function Redirect() {
        dispatch((logout()))
        keycloak.doLogout()
    }


    return (
        <Navbar bg="dark" variant="dark">
        <Container>
            <Navbar.Brand>Haushalt</Navbar.Brand>
            <Navbar.Toggle/>
            <Navbar.Collapse className="justify-content-end">
                <Nav className="me-auto">
                    <NavDropdown title="Ansicht" id="basic-nav-dropdown">
                        { groups.map(group =>
                            <NavDropdown.Item key={`Group-${group.id}`}
                                              onClick={(e) => {
                                                  e.preventDefault()
                                                  setSelectedGroup(group)
                                              }}
                            >{group.name}
                            </NavDropdown.Item>)}
                    </NavDropdown>
                    {/*   <Chat/> */}
                </Nav>
                <Button variant={"dark"} className="showSelectedGroup">{selectedGroup.name}</Button>
                <SettingsPopup getActiveGroupId={getActiveGroupId} realgroups={realGroups} groups={ groups} setSelectedSettingsGroup={setSelectedSettingsGroup} selectedSettingsGroup={selectedSettingsGroup} AddGroup={AddGroup} DeleteGroup={DeleteGroup}/>
                <Button variant="primary" className="buttonStyle" onClick={() => Redirect()}>Logout</Button>
            </Navbar.Collapse>
        </Container>
        </Navbar>
    )
}