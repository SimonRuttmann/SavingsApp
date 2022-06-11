import {Button, Card, Col, Form, Row} from "react-bootstrap";
import Select from "react-select";
import React from "react";
import makeAnimated from "react-select/animated";
export const SearchBar = ({setSelectedEntry, selectedEntry, mappedCategories, setSelectedCategories, AddEntry, setShowMore, showMore}) => {

    const animatedComponents = makeAnimated();

    return (
        <Card>
            <Card.Body>
                <Form>
                    <Row className="searchBar">
                        <Col>
                            <Form.Group>
                                <Form.Label>Name</Form.Label>
                                <Form.Control type="text" placeholder="Name eintragen" onChange={() => setSelectedEntry()} value={ selectedEntry != null ? selectedEntry.name : null} />
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group>
                                <Form.Label>Kosten</Form.Label>
                                <Form.Control type="text" placeholder="Kosten eintragen" onChange={() => setSelectedEntry()} value={selectedEntry != null ? selectedEntry.costBalance : null} />
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
                                    <Form.Control type="text" placeholder="Datum eintragen" onChange={() => setSelectedEntry()} value={selectedEntry != null ? selectedEntry.timestamp : null}/>
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