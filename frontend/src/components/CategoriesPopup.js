import React, {useEffect, useState} from "react";
import {Button, Form, Modal, ToggleButton} from "react-bootstrap";

const CategoriesPopup = ({categories, setCategories}) => {
    const [show, setShow] = useState(false);
    const [name, setName] = useState('');
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const buttonStyle = {
        float: "right",
        margin: "2px"
    }

    const createCategorie = () => {
        setCategories([...categories,
            {
                name: name,
                checked: false
            }])

    }

    return (
        <>
            <ToggleButton variant="light" onClick={handleShow}
                          size="lg"
                          className="mb-2"
                          id="toggle-check"
                          type="checkbox"
                          variant="outline-primary">
                +
            </ToggleButton>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Create Categorie</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group>
                            <Form.Label>Name</Form.Label>
                            <Form.Control type="text" placeholder="Name eintragen" onChange={e => setName(e.target.value)} value={name}/>
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={ createCategorie

                    }>
                        Create categorie
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default CategoriesPopup;