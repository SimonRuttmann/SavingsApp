import {Card, Col, Form, Row} from "react-bootstrap";
import Select from "react-select";
import React from "react";
import makeAnimated from "react-select/animated";
import {filterInformationAction} from "./Homepage";

export const SearchBar = ({   mappedCategories,
                              users,
                              currentFilterInformation,
                              dispatchFilterInformation}) => {

    const animatedComponents = makeAnimated();
    const timeWindow = [{label : "Day"},{label : "Week"},{label : "Month"},{label : "Year"}]

    return (
        <Card className="searchBar">
            <Card.Body>
                <Form>
                    <Row >
                        <Col>
                            <Form.Group>
                                <Form.Label>Zeitraum</Form.Label>
                                <div className="Select">
                                    <Select options={timeWindow}
                                            components={animatedComponents}
                                            defaultValue={currentFilterInformation.timeInterval}
                                            onChange={(e) => dispatchFilterInformation({type: filterInformationAction.changeFilterTimeInterval, payload:e})}
                                    />
                                </div>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group>
                                <Form.Label>Datum</Form.Label>
{/*
                                <Form.Control type="text" placeholder="Datum" onChange={() => setSelectedEntry()} value={selectedEntry != null ? selectedEntry.timestamp : null}/>
                                //TODO onChange={(e) => dispatchFilterInformation({type: filterInformationAction.changeStartDate, payload:e})}
                                //TODO onChange={(e) => dispatchFilterInformation({type: filterInformationAction.changeEndDate, payload:e})}
*/}
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group className="UserArea">
                                <Form.Label>Users</Form.Label>
                                <div className="Multiselect">
                                    <Select options={users}
                                            components={animatedComponents}
                                            defaultValue={currentFilterInformation.users}
                                            onChange={(e) => dispatchFilterInformation({type: filterInformationAction.changeFilterUsers, payload:e})}
                                            isMulti />
                                </div>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group className="CategoryArea">
                                <Form.Label>Kategorien</Form.Label>
                                <div className="Multiselect">
                                    <Select options={mappedCategories}
                                            components={animatedComponents}
                                            defaultValue={currentFilterInformation.categories}
                                            onChange={(e) => dispatchFilterInformation({type: filterInformationAction.changeFilterCategories, payload:e})}
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