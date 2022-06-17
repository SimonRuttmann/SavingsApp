import React, {useRef, useState} from "react";
import {Button, Card, Col, Form, Modal, Row} from "react-bootstrap";
import Select from "react-select";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { registerLocale } from  "react-datepicker";
import de from 'date-fns/locale/de';
import makeAnimated from "react-select/animated";
import {isNumberOrDecimalString} from "../../utils/util";

const UpdateSavingEntry = ({selectedEntry, updateEntry, mappedCategories, setOpenUpdateEntryPopup}) => {


    const [show, setShow] = useState(true);
    const handleClose = () => {setShow(false); setOpenUpdateEntryPopup(false)}
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
    const [startDate, setStartDate] = useState(Date.parse(selectedEntry.creationDate));
    const [costBalance, setCostBalance] = useState(selectedEntry.costBalance)

    const animatedComponents = makeAnimated();


    function handleChange(category){
        setEntryUpdateSelectedCategory(category);
    }

    function changeCostBalance(string){
        if(isNumberOrDecimalString(string)) setCostBalance(string)
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



        updateEntry(entry);
        handleClose();
    }

    return (
        <>
            <Button variant="light" style={buttonStyle} onClick={handleShow}>
                Eintrag ändern
            </Button>

            <Modal show={show} onHide={handleClose} size="xl">
                <Modal.Header closeButton>
                    <Modal.Title>Eintrag ändern</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Card>
                        <Card.Body>
                            <Form>
                                <Row className="entryCreationBar">
                                    <Col xs={"6"} md={"3"} className="mb-2">
                                        <Form.Group>
                                            <Form.Label>Name</Form.Label>
                                            <Form.Control ref={nameRef} id="name" type="text" defaultValue={selectedEntry.name}/>
                                        </Form.Group>
                                    </Col>
                                    <Col xs={"6"} md={"3"} className="mb-2">
                                        <Form.Group>
                                            <Form.Label>Kosten</Form.Label>
                                            <Form.Control ref={costBalanceRef} type="text" value={costBalance} onChange={(e) => changeCostBalance(e.target.value)}/>
                                        </Form.Group>
                                    </Col>
                                    <Col xs={"6"} md={"3"} className="mb-2">
                                        <Form.Group className="CategoryArea">
                                            <Form.Label>Kategorien</Form.Label>
                                            <div className="Select">
                                                <Select
                                                        defaultValue={mappedCategories.find(cat => cat.id === selectedEntry.category.id)}
                                                        options={mappedCategories}
                                                        components={animatedComponents}
                                                        onChange={(e) => handleChange(e)}
                                                />
                                            </div>
                                        </Form.Group>
                                    </Col>
                                    <Col xs={"6"} md={"3"} className="mb-2">
                                        <Form.Group>
                                            <Form.Label>Beschreibung</Form.Label>
                                            <Form.Control ref={descriptionRef} defaultValue={selectedEntry.description} as="textarea" rows={3}/>
                                        </Form.Group>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col xs={"6"} md={"3"} className="mb-2">
                                        <Form.Group>
                                            <Form.Label>Datum</Form.Label>
                                            <DatePicker selected={startDate}
                                                        onChange={(date) => setStartDate(date)}
                                                        locale="de"
                                                        dateFormat="dd/MM/yyyy"
                                            />
                                        </Form.Group>
                                    </Col>

                                </Row>
                                <Col className="buttonCol maxMarginLeft floatRight">
                                    <Form.Group className="buttonArea">
                                        <Button onClick={() => perpareUpdate()}>Eintrag ändern</Button>
                                    </Form.Group>
                                </Col>
                            </Form>
                        </Card.Body>
                    </Card>
                </Modal.Body>
                <Modal.Footer/>
            </Modal>
        </>

    )
}

export default UpdateSavingEntry;