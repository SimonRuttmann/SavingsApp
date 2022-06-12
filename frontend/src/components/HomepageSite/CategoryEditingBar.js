import {Button, Card, Col, Form, Row} from "react-bootstrap";
import React, {useRef, useState} from "react";
import Select from "react-select";
import makeAnimated from "react-select/animated";

const CategoryEditingBar = ({addCategory, deleteCategory, mappedCategories}) => {

    const animatedComponents = makeAnimated();

    const nameRef = useRef(null);

    const prepareCreate = () => {
        let name = nameRef.current.value;
        if(name != null && name.trim() !== "")
            addCategory({name: name});
    }


    const [deletionSelectedCategory,setDeletionSelectedCategory] = useState(null);

    function handleChange(category){
        setDeletionSelectedCategory(category);
    }

    const prepareDelete = () => {
        if(deletionSelectedCategory != null && deletionSelectedCategory.id != null)
        deleteCategory(deletionSelectedCategory.id);
        setDeletionSelectedCategory(null);
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
                        <Col>
                            <Form.Group className="CategoryArea">
                                <Form.Label>Kategorien</Form.Label>
                                <div className="Select">
                                    <Select options={mappedCategories}
                                            components={animatedComponents}
                                            onChange={(e) => handleChange(e)}
                                            value={deletionSelectedCategory}
                                    />
                                </div>
                            </Form.Group>
                        </Col>
                        <Col className="buttonCol">
                            <Form.Group className="buttonArea">
                                <Button onClick={() => prepareDelete()}>Kategorie löschen</Button>
                            </Form.Group>
                        </Col>

                    </Row>
                </Form>
            </Card.Body>
        </Card>
    )
}

export default CategoryEditingBar;