import {Button, Card, Col, Form, Row} from "react-bootstrap";
import React, {useEffect, useRef, useState} from "react";
import Select from "react-select";
import makeAnimated from "react-select/animated";

const CategoryEditingBar = ({clearSelectors, addCategory, deleteCategory, mappedCategories, updateCategory}) => {


    //Create
    const animatedComponents = makeAnimated();

    const nameRef = useRef(null);

    const prepareCreate = () => {
        let name = nameRef.current.value;
        if(name != null && name.trim() !== "")
            addCategory({name: name});
    }


    //Delete
    const [deletionSelectedCategory,setDeletionSelectedCategory] = useState(null);

    function handleChangeDelete(category){
        setDeletionSelectedCategory(category);
    }

    const prepareDelete = () => {
        if(deletionSelectedCategory != null && deletionSelectedCategory.id != null){
            deleteCategory(deletionSelectedCategory.id);
            setDeletionSelectedCategory(null);
        }
    }

    //Update
    const [updateSelectedCategory,setUpdateSelectedCategory] = useState(null);

    const updateNameRef = useRef(null);

    function handleChangeUpdate(category){
        setUpdateSelectedCategory(category);
    }

    const prepareUpdate = () => {
        let name = updateNameRef.current.value;

        if(name == null || name.trim() === "") return;

        if(updateSelectedCategory != null && updateSelectedCategory.id != null) {
            updateCategory({id: updateSelectedCategory.id, name: name});
            setUpdateSelectedCategory(null);
        }

    }

    useEffect( () => {
        setDeletionSelectedCategory(null);
        setUpdateSelectedCategory(null);
    }, [clearSelectors])


    return (
        <Card>
            <Card.Body>
                <Form>
                    <Row className="entryCreationBar">

                        <Col>
                            <Form.Group>
                                <Form.Label>Name</Form.Label>
                                <Form.Control ref={nameRef} id="name" type="text" placeholder="Kategoriename eintragen"/>
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
                                            onChange={(e) => handleChangeDelete(e)}
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

                    <br/>

                    <Row className="entryCreationBar">

                        <Col>
                            <Form.Group>
                                <Form.Label>Name</Form.Label>
                                <Form.Control ref={updateNameRef} id="name" type="text" placeholder="Neuer Kategoriename"/>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group className="CategoryArea">
                                <Form.Label>Kategorien</Form.Label>
                                <div className="Select">
                                    <Select options={mappedCategories}
                                            components={animatedComponents}
                                            onChange={(e) => handleChangeUpdate(e)}
                                            value={updateSelectedCategory}
                                    />
                                </div>
                            </Form.Group>
                        </Col>
                        <Col></Col>
                        <Col className="buttonCol">
                            <Form.Group className="buttonArea">
                                <Button onClick={() => prepareUpdate()}>Kategorie editieren</Button>
                            </Form.Group>
                        </Col>
                    </Row>
                </Form>
            </Card.Body>
        </Card>
    )
}

export default CategoryEditingBar;