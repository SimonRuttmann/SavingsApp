import React, {useEffect, useState} from "react";
import {Button, Col, Form, Modal, Row} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import {addNewGroup, leaveAGroup, selectGroupInformationStore} from "../../reduxStore/GroupInformationSlice";
import {isDisabled} from "@testing-library/user-event/dist/utils";
import {
    acceptAInvitation,
    acceptInvitation, declineAInvitation, declineInvitation,
    invitePerson,
    selectUserInvitationsStore,
    selectUserNamesStore,
    selectUserStore
} from "../../reduxStore/UserSlice";
import Select from "react-select";
import makeAnimated from "react-select/animated";
//{ getActiveGroupId, setActiveGroupId}
const SettingsPopup = ({ getActiveGroupId, setActiveGroupId, onHide,show}) => {
    console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
    const [getGroupId, setGroupId] = useState();
    const [showNewGroup, setShowNewGroup]= useState(false)
    const [showInvite, setShowInvite] = useState(false)
    const [showInvitations, setInvitations] = useState(false)
    const [choosenUsername, setChoosenUsername] = useState(false)

    console.log("status im popup: "+show)

    const animatedComponents = makeAnimated();

    //states
    const groupInformationStore = useSelector(selectGroupInformationStore);
    const userStore   = useSelector(selectUserStore);
    const dispatch = useDispatch()

    //gruppe initialisieren
    let selectedGroup
    if(getGroupId == null){
        selectedGroup = (groupInformationStore.find(group => group.id === getActiveGroupId));
    } else {
        selectedGroup = (groupInformationStore.find(group => group.id === getGroupId));
    }


    if(!Array.isArray(groupInformationStore) || getActiveGroupId == null || selectedGroup == null || selectedGroup.personDTOList == null || userStore == null) return null;
    

    const changeGroup = (nextGroup) => {
        let group = (groupInformationStore.find(group => group.id === nextGroup.id));
        setGroupId(nextGroup.id)
        if(group.personGroup === true){ setShowInvite(false) }
    }

    function addGroup(){
        let groupName = document.getElementById("groupName").value;
        if(groupName != "Ich" && groupName != "ich"){
            const newGroup = {
                "groupName": ""+groupName
            }
            dispatch(addNewGroup(newGroup))
            document.getElementById("groupName").value = ""
        }

    }

    function LeaveGroup(id) {
        let newFokus = groupInformationStore.find(group => group.personGroup === true)
        if(id === getActiveGroupId) setActiveGroupId(newFokus.groupId)
        dispatch(leaveAGroup(id))
        setGroupId(newFokus.groupId)
    }
    function DeleteGroup(id) {
        let newFokus = groupInformationStore.find(group => group.personGroup === true)
        if(id === getActiveGroupId) setActiveGroupId(newFokus.groupId)
        dispatch(leaveAGroup(id))
        setGroupId(newFokus.groupId)
    }

    //check if username valid
    function updateUsernameOptions(){
        let options = []
        userStore.usernames.map(username =>{
            if (selectedGroup.personDTOList.find(person => person.username === username) === undefined ){
                let option = { value: username , label: username }
                options.push(option)
            }
        })
        return options;
    }

    function handleChangeUsername(username){
        setChoosenUsername(username)
    }


    function inviteUser() {

        const newInvite = {
            "username": ""+choosenUsername.value,
            "groupId": ""+getGroupId
        }
        dispatch(invitePerson(newInvite))
        setChoosenUsername(null)
    }

    function acceptThisInvitation(groupId) {
        dispatch(acceptAInvitation(groupId))
    }

    function declineThisInvitation(groupId) {
        dispatch(declineAInvitation(groupId))
    }

    return (


    <Modal show={show} onHide={onHide} >
        <Modal.Header closeButton>
            <Modal.Title>Einstellungen</Modal.Title>
        </Modal.Header>
        <Modal.Body className = {"settingPopUp"}>
            {showNewGroup &&
            <Col className="mt-2" md={12}>
                <h6>Neue Gruppe erstellen:</h6>
                <div className="row">
                    <Col>
                        <Form.Group>
                            <Form.Control  id="groupName" type="text" placeholder="Gruppenname"/>
                        </Form.Group>
                    </Col>
                    <Col>
                        <Button className={"btn btn-success"} onClick={ () => addGroup()} variant="secondary">Bestätigen</Button>
                    </Col>
                    <Col>
                        <Button onClick={ () => setShowNewGroup(false)} variant="secondary">Abbrechen</Button>
                    </Col>
                </div>
            </Col>
            }

            <div className={"row mt-2 col-md-12"} >
                { !showNewGroup && <Button className={"addGroupButton"} onClick={ () => setShowNewGroup(true)} variant="secondary">Gruppe hinzufügen</Button>}
            </div>
            {showInvite &&
            <div className="mt-2 col-md-12" >
                <h6>In Gruppe "{selectedGroup.groupName}" einladen:</h6>
                <div className="row">
                    <Col xs={"6"} md={"4"}>
                        <Form.Group className="CategoryArea">
                            <Form.Label>Username</Form.Label>
                            <div className="Select">
                                <Select
                                        options={updateUsernameOptions()}
                                        components={animatedComponents}
                                        onChange={(e) =>  handleChangeUsername(e)}
                                        value={choosenUsername}
                                />
                            </div>
                        </Form.Group>
                    </Col>
                    <Col>
                        <Button className={"btn btn-success"}  onClick={ () => inviteUser()} variant="secondary">Bestätigen</Button>
                    </Col>
                    <Col>
                        <Button onClick={ () => setShowInvite(false)} variant="secondary">Abbrechen</Button>
                    </Col>
                </div>
            </div>
            }
            <div className={"row mt-2 col-md-12"}>
            { !showInvite && <Button disabled={!!selectedGroup.personGroup} className={"addGroupButton"} onClick={ () => setShowInvite(true)} variant="secondary">Zur Gruppe Einladen</Button>}
            </div>
            { showInvitations &&
            <div className="mt-2 col-md-12" >
                <Row>
                    <h5>Einladungen:</h5>
                    <div className={""} >
                        <Button className={"mb-2 col-md-12 btn-sm addGroupButton"} onClick={ () => setInvitations(false)} variant="secondary">Einladungen verbergen</Button>
                    </div>
                </Row>
                { userStore.invitations.map(invitation =>
                    <div key={`Settings-Invitation-div-${invitation.groupId}`}>
                        <Row key={`Settings-Invitation-Row-${invitation.groupId}`}>
                            <Col  key={`Settings-Invitation-Col1-${invitation.groupId}`}>
                                <h6 key={`Settings-Invitation-${invitation.groupId}`}>{invitation.groupName}</h6>
                            </Col>
                            <Col  key={`Settings-Invitation-Col2-${invitation.groupId}`}>
                                <Button className={"btn btn-success"} key={`Settings-Inv-But-1-${invitation.groupId}`} onClick={ () => acceptThisInvitation(invitation.groupId) } variant="secondary">Akzeptieren</Button>
                            </Col>
                            <Col  key={`Settings-Invitation-Col3-${invitation.groupId}`}>
                                <Button className={"btn btn-danger"}key={`Settings-Inv-But-2-${invitation.groupId}`} onClick={ () => declineThisInvitation(invitation.groupId)} variant="secondary">Ablehnen</Button>
                            </Col>
                        </Row>
                    </div>
                )}
            </div>
            }
            <div className={"row mt-2 col-md-12"}>
                { !showInvitations && <Button className={"addGroupButton"} onClick={ () => setInvitations(true)} variant="secondary">Meine Einladungen</Button>}
            </div>

            <h5 styleclass={"topMarginText"}>Meine Gruppen:</h5>
            { groupInformationStore.map(group =>
                <h6 className={selectedGroup != null && selectedGroup.id === group.id ? "selectedGroup" : ""} onClick={ () => changeGroup(group)} key={`Settings-Group-${group.id}`}>{group.groupName}</h6>)}
            <h5>Mitglieder:</h5>
            { selectedGroup.personDTOList.map(user => <h6 key={`Settings-Group-${user.username}`}>{user.username}</h6>)}
            <div className="row">
            <Col>
            <Button disabled={!!selectedGroup.personGroup} onClick={ () => LeaveGroup(selectedGroup.id)} variant="secondary">Gruppe Verlassen</Button>
            </Col>
            <Col>
                <Button disabled={!!selectedGroup.personGroup} onClick={ () => DeleteGroup(selectedGroup.id)} variant="secondary">Gruppe löschen</Button>
            </Col>
            </div>
        </Modal.Body>
        <Modal.Footer>
        </Modal.Footer>
    </Modal>

    );
};

export default SettingsPopup;
