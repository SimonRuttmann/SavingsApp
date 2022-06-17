import {Button, Card, Col, Form, Row} from "react-bootstrap";
import Select from "react-select";
import React, {useEffect, useRef, useState} from "react";
import makeAnimated from "react-select/animated";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { registerLocale } from  "react-datepicker";
import de from 'date-fns/locale/de';
import {isNumberOrDecimalString} from "../../utils/util";


export const EntryCreationBar = ({ mappedCategories, AddEntry, setShowMore, showMore, clearSelectors, updateCategorySelector}) => {


    registerLocale('de', de)

    const nameRef = useRef(null);
    const costBalanceRef = useRef(null);
    const descriptionRef = useRef(null);

    const [entryCreationSelectedCategory,setEntryCreationSelectedCategory] = useState(null);
    const [startDate, setStartDate] = useState(new Date());
    const [costBalance, setCostBalance] = useState("0.00")

    const animatedComponents = makeAnimated();

    useEffect( () => {
        setEntryCreationSelectedCategory(null);
    }, [clearSelectors])

    useEffect( () => {
        if(updateCategorySelector != null &&
            entryCreationSelectedCategory != null &&
            entryCreationSelectedCategory.id === updateCategorySelector){

            setEntryCreationSelectedCategory(null);
        }
        
        // eslint-disable-next-line
    }, [updateCategorySelector])


    function handleChange(category){
        setEntryCreationSelectedCategory(category);
    }

    function changeCostBalance(string){
        if(isNumberOrDecimalString(string)) setCostBalance(string)
    }

    function addEntry(){
        let name = nameRef.current.value;
        let costBalance = costBalanceRef.current.value;
        let creationDate = startDate;
        let description;
        let category = entryCreationSelectedCategory;

        if(category == null && mappedCategories.length > 0)
             category = null;

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
        <>
        <Card className="subHeader">
            <h4>Eintragserstellung</h4>
        </Card>
        <Card>
            <Card.Body>
                <Form>
                    <Row className="entryCreationBar">
                        <Col xs={4} md={3}>
                            <Form.Group>
                                <Form.Label>Name</Form.Label>
                                <Form.Control ref={nameRef} id="name" type="text" placeholder="Eintragsname eintragen"/>
                            </Form.Group>
                        </Col>
                        <Col xs={4} md={3}>
                            <Form.Group>
                                <Form.Label>Kosten</Form.Label>
                                <Form.Control ref={costBalanceRef} type="text" placeholder="Kosten eintragen" value={costBalance} onChange={(e)=>changeCostBalance(e.target.value)}/>
                            </Form.Group>
                        </Col>
                        <Col xs={4} md={3}>
                            <Form.Group className="CategoryArea">
                                <Form.Label>Kategorien</Form.Label>
                                <div className="Select">
                                    <Select options={mappedCategories}
                                            components={animatedComponents}
                                            onChange={(e) => handleChange(e)}
                                            value = {entryCreationSelectedCategory}
                                            />
                                </div>
                            </Form.Group>
                        </Col>
                        <Col className="buttonCol row mt-2"  xs={12} md={3}>
                            <Col>
                                <Button onClick={() => addEntry()}>Eintrag erstellen</Button>
                            </Col>
                            <Col className="buttonArea" xs={6}>
                                <Button variant="link" onClick={() => setShowMore(!showMore)}>{showMore?"Zeige weniger":"Zeig mehr"}</Button>
                            </Col>
                        </Col>
                    </Row>
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
                        </Row>}
                </Form>
            </Card.Body>
        </Card>
        </>
    )
}