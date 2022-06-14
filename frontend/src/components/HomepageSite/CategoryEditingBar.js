import {Button, Card, Col, Form, Row} from "react-bootstrap";
import React, {useEffect, useRef, useState} from "react";
import Select from "react-select";
import makeAnimated from "react-select/animated";

const CategoryEditingBar = ({clearSelectors, addCategory, deleteCategory, mappedCategories, updateCategory,updateCategorySelector}) => {


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

    useEffect( () => {
        if(updateCategorySelector != null &&
            deletionSelectedCategory != null &&
            deletionSelectedCategory.id === updateCategorySelector){

            setDeletionSelectedCategory(null);
        }

        // eslint-disable-next-line
    }, [updateCategorySelector])


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

    useEffect( () => {
        if(updateCategorySelector != null &&
            updateSelectedCategory != null &&
            updateSelectedCategory.id === updateCategorySelector){

            setUpdateSelectedCategory(null);
        }


        // eslint-disable-next-line
    }, [updateCategorySelector])


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
        <>
        <Card className="subHeader">
            <h4>Kategoriebearbeitung</h4>
        </Card>
        <Card>
            <Card.Body>
                <Form>
                    <Row className="entryCreationBar">
                        <Col xs={"6"} md={3} className="element">
                            <Form.Group>
                                <Form.Label>Name</Form.Label>
                                <Form.Control ref={nameRef} id="name" type="text" placeholder="Kategoriename eintragen"/>
                            </Form.Group>
                        </Col>
                        <Col xs={"6"} md={3} className="buttonCol element">
                            <Form.Group className="buttonArea">
                                <Button onClick={() => prepareCreate()}>Kategorie erstellen</Button>
                            </Form.Group>
                        </Col>
                        <Col xs={"6"} md={3}>
                            <Form.Group className="CategoryArea element">
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
                        <Col xs={"6"} md={3} className="buttonCol element">
                            <Form.Group className="buttonArea">
                                <Button onClick={() => prepareDelete()}>Kategorie löschen</Button>
                            </Form.Group>
                        </Col>
                    </Row>
                    <br/>
                    <Row className="entryCreationBar">
                        <Col xs={"6"} md={"4"}>
                            <Form.Group>
                                <Form.Label>Name</Form.Label>
                                <Form.Control ref={updateNameRef} id="name" type="text" placeholder="Neuer Kategoriename"/>
                            </Form.Group>
                        </Col>
                        <Col xs={"6"} md={"4"}>
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
                        <Col xs={"12"} md={"4"} className="buttonCol">
                            <Form.Group className="buttonArea mt-2 ">
                                <Button onClick={() => prepareUpdate()}>Kategorie editieren</Button>
                            </Form.Group>
                        </Col>
                    </Row>
                </Form>
            </Card.Body>
        </Card>
        </>
    )
}

export default CategoryEditingBar;