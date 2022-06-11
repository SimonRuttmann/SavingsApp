import {Button, Card, Col, Form, Row} from "react-bootstrap";
import Select from "react-select";
import React, {useRef} from "react";
import makeAnimated from "react-select/animated";
export const EntryCreationBar = ({setSelectedEntry, selectedEntry, mappedCategories, entry, setEntry, AddEntry, entryAction, setShowMore, showMore}) => {

    const nameRef = useRef(null);
    const costBalanceRef = useRef(null);
    const creationDateRef = useRef(null);
    const descriptionRef = useRef(null);

    const animatedComponents = makeAnimated();

    function addEntry(){
        setEntry({type:entryAction.updateEntryName,payload:nameRef.current.value});
        setEntry({type:entryAction.updateEntryCostBalance,payload: costBalanceRef.current.value});

        if( creationDateRef.current == null || creationDateRef.current.value === "")
            setEntry({type:entryAction.updateEntryCreationDate,payload: Date.now()});
        else
            setEntry({type:entryAction.updateEntryCreationDate,payload: creationDateRef.current.value});

        if( descriptionRef.current == null || descriptionRef.current.value === "")
            setEntry({type:entryAction.updateEntryDesc,payload: null});
        else
            setEntry({type:entryAction.updateEntryDesc,payload: descriptionRef.current.value});

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
                                    <Select options={mappedCategories} components={animatedComponents} onChange={(e) => setEntry({type:"updateEntryCategory", payload:e})}
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
                                    <Form.Control ref={creationDateRef} type="text" placeholder="Datum eintragen"/>
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