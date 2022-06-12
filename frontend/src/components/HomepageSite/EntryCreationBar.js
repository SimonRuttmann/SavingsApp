import {Button, Card, Col, Form, Row} from "react-bootstrap";
import Select from "react-select";
import React, {useRef, useState} from "react";
import makeAnimated from "react-select/animated";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { registerLocale } from  "react-datepicker";
import de from 'date-fns/locale/de';


export const EntryCreationBar = ({selectedEntry, mappedCategories, AddEntry, setShowMore, showMore}) => {

    registerLocale('de', de)

    const nameRef = useRef(null);
    const costBalanceRef = useRef(null);
    const descriptionRef = useRef(null);

    const [entryCreationSelectedCategory,setEntryCreationSelectedCategory] = useState(null);
    const [startDate, setStartDate] = useState(new Date());

    const animatedComponents = makeAnimated();


    function handleChange(category){
        setEntryCreationSelectedCategory(category);
    }

    function addEntry(){
        let name = nameRef.current.value;
        let costBalance = costBalanceRef.current.value;
        let creationDate = startDate;
        let description;
        let category = entryCreationSelectedCategory;

        if(category == null && mappedCategories.length > 0)
            category = mappedCategories[0]

        if( descriptionRef.current == null || descriptionRef.current.value === "")
            description = null;
        else
            description = descriptionRef.current.value;

        let entry = {
            name: name,
            description: description,
            costBalance: costBalance,
            creationDate: creationDate,
            category: category,
            creator: null
        }

        AddEntry(entry)
    }

    return (
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
                                <Form.Control ref={costBalanceRef} type="text" placeholder="Kosten eintragen"/>
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
                                <Button onClick={() => addEntry()}>Eintrag erstellen</Button>
                                {!showMore &&  <Button variant="link" onClick={() => setShowMore(true)}>Zeig mehr</Button>}
                            </Form.Group>
                        </Col>
                    </Row>
                    <br/>
                    { showMore &&
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
                            <Col>
                                <Form.Group>
                                    <Button variant="link" onClick={() => setShowMore(false)}>Zeig weniger</Button>
                                </Form.Group>
                            </Col>
                        </Row>}
                </Form>
            </Card.Body>
        </Card>
    )
}