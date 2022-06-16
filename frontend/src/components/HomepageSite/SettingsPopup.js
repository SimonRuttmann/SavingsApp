import React, {useEffect, useRef, useState} from "react";
import {Button, Col, Form, Modal, Row} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import {
    addGroup, addGroupFromInvitation,
    addNewGroup,
    fetchGeneralInformationToGroupFromServer, fetchGroupCoreInformationFromServer,
    leaveAGroup,
    selectGroupInformationStore
} from "../../reduxStore/GroupInformationSlice";
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
//notifications
import {NotificationManager} from 'react-notifications';
import 'react-notifications/lib/notifications.css';
//{ getActiveGroupId, setActiveGroupId}
const SettingsPopup = ({ getActiveGroupId, setActiveGroupId, onHide,show}) => {
    const [getGroupId, setGroupId] = useState();
    const [showNewGroup, setShowNewGroup]= useState(false)
    const [showInvite, setShowInvite] = useState(false)
    const [showInvitations, setInvitations] = useState(false)
    const [choosenUsername, setChoosenUsername] = useState(false)
    const [newGroupName, setNewGroupName] = useState(null)


    const animatedComponents = makeAnimated();

    //states
    const groupInformationStore = useSelector(selectGroupInformationStore);
    const userStore   = useSelector(selectUserStore);
    const dispatch = useDispatch()

    const groupNameRef = useRef(null)

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

        let newGroupNameInput = groupNameRef.current.value;

        if(newGroupNameInput.length>10) {
            NotificationManager.warning("Maximal 10 Zeichen erlaubt", "Gruppenname ist zulang" );
            return;
        }
        if(newGroupNameInput !== "Ich" && newGroupNameInput !== "ich"){
            const newGroup = {
                "groupName": ""+newGroupNameInput
            }
            dispatch(addNewGroup(newGroup)).then(id => {
                dispatch(fetchGeneralInformationToGroupFromServer(id))
            } )
            groupNameRef.current.value = ""
            NotificationManager.success("erfolgreich erstellt", "Neue Gruppe '"+newGroupNameInput+"'" );
            //setTimeout(() => setNewGroupName(newGroupNameInput), 100)
            //dispatch(fetchGeneralInformationToGroupFromServer(group.id))
        } else {
            NotificationManager.warning("'Ich' darf nicht verwendet werden", "Invalider Gruppenname" );
        }
    }

    // if(newGroupName != null && groupInformationStore.find(group => group.groupName === newGroupName) === null) return null
    //
    // if(groupInformationStore.find(group => group.groupName === newGroupName)){
    //     let group = groupInformationStore.find(group => group.groupName === newGroupName)
    //     dispatch(fetchGeneralInformationToGroupFromServer(group.id))
    //     setNewGroupName(null)
    // }

    function LeaveGroup(id, gruppenname) {
        let newFokus = groupInformationStore.find(group => group.personGroup === true)
        if(id === getActiveGroupId) setActiveGroupId(newFokus.id)
        dispatch(leaveAGroup(id))
        setGroupId(newFokus.id)
        NotificationManager.success("und aus der Liste entfernt", "Gruppe '"+gruppenname+"' verlassen" );
    }
    function DeleteGroup(id, gruppenname) {
        let newFokus = groupInformationStore.find(group => group.personGroup === true)
        if(id === getActiveGroupId) setActiveGroupId(newFokus.id)
        dispatch(leaveAGroup(id))
        setGroupId(newFokus.id)
        NotificationManager.success("und aus der Liste entfernt", "Gruppe '"+gruppenname+"' gelöscht" );
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
        NotificationManager.success(choosenUsername.value+" wurde eingeladen", "Einladung abgeschickt" );
        setChoosenUsername(null)
    }

    function acceptThisInvitation(invitation) {
        dispatch(acceptAInvitation(invitation.groupId))
        const toAddGroup = {
            "groupName": ""+invitation.groupName,
            "id" : invitation.groupId,
            "personGroup": false
        }
        dispatch( addGroupFromInvitation(toAddGroup)).then( () =>
            dispatch(fetchGeneralInformationToGroupFromServer(invitation.groupId))
        )
        NotificationManager.success(choosenUsername.value+" wurde eingeladen", "Einladung abgeschickt" );
        setTimeout(() => setNewGroupName(invitation.groupName), 100)
    }

    function declineThisInvitation(groupId) {
        dispatch(declineAInvitation(groupId))
        //setNewGroupName(groupName)
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
                            <Form.Control ref={groupNameRef}  type="text" placeholder="Gruppenname"/>
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
                                <Button className={"btn btn-success"} key={`Settings-Inv-But-1-${invitation.groupId}`} onClick={ () => acceptThisInvitation(invitation) } variant="secondary">Akzeptieren</Button>
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
            <Button disabled={!!selectedGroup.personGroup} onClick={ () => LeaveGroup(selectedGroup.id, selectedGroup.groupName)} variant="secondary">Gruppe Verlassen</Button>
            </Col>
            <Col>
                <Button disabled={!!selectedGroup.personGroup} onClick={ () => DeleteGroup(selectedGroup.id, selectedGroup.groupName)} variant="secondary">Gruppe löschen</Button>
            </Col>
            </div>
        </Modal.Body>
        <Modal.Footer>
        </Modal.Footer>
</Modal>
    );
};

export default SettingsPopup;
