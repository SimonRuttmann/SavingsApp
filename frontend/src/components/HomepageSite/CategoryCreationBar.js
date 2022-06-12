import {Button, Card, Col, Form, Row} from "react-bootstrap";
import React, {useRef} from "react";

const CategoryCreationBar = ({addCategory}) => {

    const nameRef = useRef(null);

    const prepareCreate = () => {
        let name = nameRef.current.value;
        if(name != null && name.trim() !== "")
            addCategory({name: name});
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
                        <Col className="buttonCol">
                            <Form.Group className="buttonArea">
                                <Button onClick={() => prepareCreate()}>Kategorie erstellen</Button>
                            </Form.Group>
                        </Col>
                    </Row>
                </Form>
            </Card.Body>
        </Card>
    )
}

export default CategoryCreationBar;