import {Button, Card, Col, Form, Row} from "react-bootstrap";
import Select from "react-select";
import React, {useState} from "react";
import makeAnimated from "react-select/animated";
import {filterInformationAction} from "./Homepage";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { registerLocale } from  "react-datepicker";
import de from 'date-fns/locale/de';

export const SearchBar = ({   mappedCategories,
                              users,
                              currentFilterInformation,
                              dispatchFilterInformation}) => {

    registerLocale('de', de)

    const animatedComponents = makeAnimated();
    const timeWindow = [{label : "Day"},{label : "Week"},{label : "Month"},{label : "Year"}]

    const [startDate, setStartDate] = useState(new Date().setFullYear(new Date().getFullYear() - 1));

    const mapUsers = () =>{
        return users.map(user =>{ return {label: user.username,value: user.id, ...user}});
    }

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
                                <Form.Label>Start-Datum</Form.Label>
                                    <DatePicker selected={startDate}
                                                onChange={(date) => {
                                                    setStartDate(date);
                                                    dispatchFilterInformation({type: filterInformationAction.changeStartDate, payload:date});
                                                    }}
                                                locale="de"
                                                dateFormat="dd/MM/yyyy"
                                    />
{/*
                                //TODO onChange={(e) => dispatchFilterInformation({type: filterInformationAction.changeEndDate, payload:e})}
*/}
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group className="UserArea">
                                <Form.Label>Users</Form.Label>
                                <div className="Multiselect">
                                    <Select options={mapUsers()}
                                            components={animatedComponents}
                                            defaultValue={mapUsers()}
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
                                            defaultValue={currentFilterInformation.filterCategory}
                                            onChange={(e) => dispatchFilterInformation({type: filterInformationAction.changeFilterCategories, payload:e})}
                                            isMulti />
                                </div>
                            </Form.Group>
                        </Col>
                        <Col className="buttonCol">
                            <Form.Group className="buttonArea">
                                <Button >Refresh Data</Button>
                            </Form.Group>
                        </Col>
                    </Row>
                </Form>
            </Card.Body>
        </Card>
    )
}