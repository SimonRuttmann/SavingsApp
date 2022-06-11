import {Card, Col, Form, Row} from "react-bootstrap";
import Select from "react-select";
import React from "react";
import makeAnimated from "react-select/animated";
import {Title} from "chart.js";
export const SearchBar = ({
                              mappedCategories,
                              users, selectedUsers, setSelectedUsers,
                              selectedFilterCategories, setSelectedFilterCategories,
                              timeWindow, selectedTimeWindow, setSelectedTimeWindow}) => {

    const animatedComponents = makeAnimated();

    return (
        <Card className="searchBar">
            <Card.Body>
                <Form>
                    <Row >
                        <Col>
                            <Form.Group>
                                <Form.Label>Zeitraum</Form.Label>
                                <div className="Select">
                                    <Select options={timeWindow} components={animatedComponents} defaultValue={selectedTimeWindow}  onChange={(e) => setSelectedTimeWindow(e)}
                                            />
                                </div>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group>
                                <Form.Label>Datum</Form.Label>
{/*
                                <Form.Control type="text" placeholder="Datum" onChange={() => setSelectedEntry()} value={selectedEntry != null ? selectedEntry.timestamp : null}/>
*/}
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group className="UserArea">
                                <Form.Label>Users</Form.Label>
                                <div className="Multiselect">
                                    <Select options={users} components={animatedComponents} defaultValue={selectedUsers} onChange={(e) => setSelectedUsers(e)}
                                            isMulti />
                                </div>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group className="CategoryArea">
                                <Form.Label>Kategorien</Form.Label>
                                <div className="Multiselect">
                                    <Select options={mappedCategories} components={animatedComponents} defaultValue={selectedFilterCategories} onChange={(e) => setSelectedFilterCategories(e)}
                                            isMulti />
                                </div>
                            </Form.Group>
                        </Col>
                    </Row>
                </Form>
            </Card.Body>
        </Card>
    )
}