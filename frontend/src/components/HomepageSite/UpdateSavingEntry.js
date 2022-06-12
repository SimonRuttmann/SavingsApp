import React, {useRef, useState} from "react";
import {Button, Card, Col, Form, Modal, Row} from "react-bootstrap";
import Select from "react-select";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { registerLocale } from  "react-datepicker";
import de from 'date-fns/locale/de';
import makeAnimated from "react-select/animated";

const UpdateSavingEntry = (selectedEntry, updateEntry, mappedCategories) => {
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    registerLocale('de', de)

    const buttonStyle = {
        float: "right",
        margin: "2px"
    }


    const nameRef = useRef(null);
    const costBalanceRef = useRef(null);
    const descriptionRef = useRef(null);

    const [entryUpdateSelectedCategory,setEntryUpdateSelectedCategory] = useState(selectedEntry.category);
    const [startDate, setStartDate] = useState(new Date());

    const animatedComponents = makeAnimated();


    function handleChange(category){
        setEntryUpdateSelectedCategory(category);
    }



    function perpareUpdate(){
        let name = nameRef.current.value;
        let costBalance = costBalanceRef.current.value;
        let creationDate = startDate;
        let description;
        let category = entryUpdateSelectedCategory;

        if(category == null && mappedCategories.length > 0)
            category = mappedCategories[0]

        if( descriptionRef.current == null || descriptionRef.current.value === "")
            description = null;
        else
            description = descriptionRef.current.value;

        let entry = {
            id: selectedEntry.id,
            name: name,
            description: description,
            costBalance: costBalance,
            creationDate: creationDate,
            category: category,
            creator: selectedEntry.creator
        }

        updateEntry(selectedEntry);
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
                    <Card>
                        <Card.Body>
                            <Form>
                                <Row className="entryCreationBar">
                                    <Col>
                                        <Form.Group>
                                            <Form.Label>Name</Form.Label>
                                            <Form.Control ref={nameRef} id="name" type="text" placeholder="Name eintragen"/>
                                        </Form.Group>
                                    </Col>
                                    <Col>
                                        <Form.Group>
                                            <Form.Label>Kosten</Form.Label>
                                            <Form.Control ref={costBalanceRef} type="text" defaultValue={selectedEntry.costBalance}/>
                                        </Form.Group>
                                    </Col>
                                    <Col>
                                        <Form.Group className="CategoryArea">
                                            <Form.Label>Kategorien</Form.Label>
                                            <div className="Select">
                                                <Select options={mappedCategories} components={animatedComponents} onChange={(e) => handleChange(e)}
                                                />
                                            </div>
                                        </Form.Group>
                                    </Col>
                                    <Col className="buttonCol">
                                        <Form.Group className="buttonArea">
                                            <Button onClick={() => perpareUpdate()}>Eintrag erstellen</Button>

                                        </Form.Group>
                                    </Col>
                                </Row>
                                <br/>
                                    <Row>
                                        <Col>
                                            <Form.Group>
                                                <Form.Label>Datum</Form.Label>
                                                <DatePicker selected={startDate}
                                                            onChange={(date) => setStartDate(date)}
                                                            locale="de"
                                                            dateFormat="dd/MM/yyyy"
                                                />
                                            </Form.Group>
                                        </Col>
                                        <Col>
                                            <Form.Group>
                                                <Form.Label>Beschreibung</Form.Label>
                                                <Form.Control ref={descriptionRef} as="textarea" rows={3}/>
                                            </Form.Group>
                                        </Col>
                                    </Row>
                            </Form>
                        </Card.Body>
                    </Card>
                </Modal.Body>
                <Modal.Footer>
                </Modal.Footer>
            </Modal>
        </>

    )
}

export default UpdateSavingEntry;