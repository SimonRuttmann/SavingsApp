import React, {useState} from "react";
import {Button, Modal} from "react-bootstrap";

const SettingsPopup = ({groups, setSelectedSettingsGroup, selectedSettingsGroup, AddGroup, DeleteGroup}) => {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const buttonStyle = {
        float: "right",
        margin: "2px"
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
        <Modal.Body>
            { groups.map(group => <h6 key={`Settings-Group-${group.id}`} onClick={(e) => {
                e.preventDefault()
                setSelectedSettingsGroup(group)
            }} href={group.name}>{group.name}</h6>)}
            <h5>Mitglieder:</h5>
            {selectedSettingsGroup.members.map(user => <h6 key={`Settings-Group-${user.name}`}>{user}</h6>)}
            <Button onClick={() => AddGroup({name: 'Familie2',
                diagrams: {
                    diagram1: {firstValue: 1852, secondValue: 1219},
                    diagram2: {firstValues: [11, 22, 33, 44, 55, 66], secondValues: [66, 55, 44, 33, 22, 11]},
                    diagram3: {firstValue: 1813, secondValue: 110}
                },
                members: ['Robin', 'Ralf', 'Maria']
            })} variant="secondary">Gruppe hinzufügen</Button>
            <Button onClick={ () => DeleteGroup(selectedSettingsGroup.name)} variant="secondary">Gruppe Verlassen</Button>
        </Modal.Body>
        <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
                Close
            </Button>
            <Button variant="primary" onClick={handleClose}>
                Save Changes
            </Button>
        </Modal.Footer>
    </Modal>
        </>
    );
};

export default SettingsPopup;