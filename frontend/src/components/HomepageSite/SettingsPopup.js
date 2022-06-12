import React, {useState} from "react";
import {Button, Modal} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import {addNewGroup, leaveAGroup, selectGroupInformationStore} from "../../reduxStore/GroupInformationSlice";
import {isDisabled} from "@testing-library/user-event/dist/utils";

const SettingsPopup = ({getActiveGroupId}) => {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const [getGroupId, setGroupId] = useState();

    //states
    const groupInformationStore = useSelector(selectGroupInformationStore);
    // if(groupInformationStore == null || !Array.isArray(groupInformationStore)) return null;
    // if( getActiveGroupId == null) return null;
    // if( )
    const dispatch = useDispatch()
    //gruppe initialisieren
    let selectedGroup
    if(getGroupId == null){
        selectedGroup= (groupInformationStore.find(group => group.id === getActiveGroupId));
    } else {
        selectedGroup= (groupInformationStore.find(group => group.id === getGroupId));
    }


    if(!Array.isArray(groupInformationStore) || getActiveGroupId == null || selectedGroup == null || selectedGroup.personDTOList == null) return null;



        //tests


    const getInformation = () => {


    }
    getInformation()

    const buttonStyle = {
        float: "right",
        margin: "2px"
    }

    function changeGroup(nextGroup){
        (groupInformationStore.find(group => group.id === nextGroup.id));
        setGroupId(nextGroup.id)
    }

    function addGroup(){
        let groupName = document.getElementById("groupName").value;
        const newGroup = {
            "groupName": ""+groupName
        }
        dispatch(addNewGroup(newGroup))
    }

    function LeaveGroup(id) {
        console.log("Guppenid "+id);
        dispatch(leaveAGroup(id))
        let newFokus = (groupInformationStore.find(group => group.personGroup === true))
        setGroupId(newFokus)
    }
    function DeleteGroup(id) {
        console.log("Delete Guppenid "+id);
        dispatch(leaveAGroup(id))
        let newFokus = (groupInformationStore.find(group => group.personGroup === true))
        setGroupId(newFokus)
    }

    function openToAddGroup() {
        //is opening a little Box to insert new Groupname

    }

    return (
        <>
        <Button variant="light" style={buttonStyle} onClick={handleShow}>
            Einstellungen
        </Button>

    <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
            <Modal.Title>Einstellungen</Modal.Title>
        </Modal.Header>
        <Modal.Body className = {"settingPopUp"}>
            <div id="newGroup">
                <form className={"forumSize"}>
                    <input id="groupName" type="text" name="name" placeholder="Gruppennamen eingeben" />
                    <Button  onClick={ () => addGroup()} variant="secondary">Bestätigen</Button>
                </form>
                <div className={"addGroupButtonDiv"}><Button className={"addGroupButton"} onClick={ () => openToAddGroup()} variant="secondary">Gruppe hinzufügen</Button>
                </div>
            </div>
             <h5 styleclass={"topMarginText"} >Meine Gruppen:</h5>
            { groupInformationStore.map(group =>
                <h6 className={selectedGroup != null && selectedGroup.id === group.id ? "selectedGroup" : ""} onClick={ () => changeGroup(group)} key={`Settings-Group-${group.id}`}>{group.groupName}</h6>)}
            <h5>Mitglieder:</h5>
            { selectedGroup.personDTOList.map(user => <h6 key={`Settings-Group-${user.username}`}>{user.username}</h6>)}
            <Button onClick={ () => addGroup()} variant="secondary">Gruppe hinzufügen</Button>
            <Button disabled={!!selectedGroup.personGroup} onClick={ () => LeaveGroup(selectedGroup.id)} variant="secondary">Gruppe Verlassen</Button>
            <Button disabled={!!selectedGroup.personGroup} onClick={ () => DeleteGroup(selectedGroup.id)} variant="secondary">Gruppe löschen</Button>
        </Modal.Body>
        <Modal.Footer>
        </Modal.Footer>
    </Modal>
        </>
    );
};

export default SettingsPopup;