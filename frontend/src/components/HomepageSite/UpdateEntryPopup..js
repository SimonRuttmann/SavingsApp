import React, {useEffect, useState} from "react";
import {Button, Card, CardGroup, Col, Form, Modal, Row, ToggleButton} from "react-bootstrap";
import {SearchBar} from "./SearchBar";
import Select from "react-select";
import makeAnimated from "react-select/animated";

export const UpdateEntryPopup = ({setSelectedEntry, selectedEntry, mappedCategories, setSelectedCategories, AddEntry, setShowMore, showMore}) => {
    const animatedComponents = makeAnimated();
    return (
        <Card>
            <Card.Body>
                <Form>
                    <Row className="searchBar">
                        <Col>
                            <Form.Group>
                                <Form.Label>Name</Form.Label>
                                <Form.Control type="text" placeholder="Name eintragen" onChange={() => setSelectedEntry()} value={selectedEntry.name} />
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group>
                                <Form.Label>Kosten</Form.Label>
                                <Form.Control type="text" placeholder="Kosten eintragen" onChange={() => setSelectedEntry()} value={selectedEntry.costs} />
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group className="CategoryArea">
                                <Form.Label>Kategorien</Form.Label>
                                <div className="Multiselect">
                                    <Select options={mappedCategories} components={animatedComponents} onChange={(e) => setSelectedCategories(e)}
                                            isMulti />
                                </div>
                            </Form.Group>
                        </Col>
                        <Col className="buttonCol">
                            <Form.Group className="buttonArea">
                                <Button onClick={() => AddEntry(selectedEntry)}>Eintrag erstellen</Button>
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
                                <Form.Control type="text" placeholder="Datum eintragen" onChange={() => setSelectedEntry()} value={selectedEntry.timestamp}/>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group>
                                <Form.Label>Beschreibung</Form.Label>
                                <Form.Control as="textarea" rows={3} onChange={() => setSelectedEntry()} />
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