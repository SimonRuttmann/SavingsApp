import React, {useEffect, useState} from "react";
import {Chart as ChartJS, ArcElement, Tooltip, Legend} from 'chart.js';
import "./styles.css"
import {Button, ButtonGroup, Card, CardGroup} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import data from "bootstrap/js/src/dom/data";

ChartJS.register(ArcElement, Tooltip, Legend);

const writingStyle = {
    textAlign: "center",
    padding: "1%",
    border: "0"
}

const buttonStyle = {
    float: "right",
    marginbottom: "5%",
}

const state = {
    Diagramm2: '0',
    Diagramm3: '0',
    Diagramm1: '0'
}

const GuestSite = ({setGuestSite}) => {

    // componentDidMount() {
    //     fetch('http://localhost:8080/global')
    //         .then(response => response.json())
    //         .then(data => this.setState(data))
    // }

        return (
            <>
                <ButtonGroup style={buttonStyle}>
                    <Button variant="secondary" onClick={() => setGuestSite(false)}>Login</Button>
                    <Button variant="secondary">Register</Button>
                </ButtonGroup>
                <br/>
                <br/>
                <br/>
                <br/>
                <Card style={writingStyle}>
                    <Card.Text>Hier steht ein Slogan für wundervolle Menschen.</Card.Text>
                </Card>
                <br/>
                <br/>
                <br/>
                <CardGroup>
                    <Card style={writingStyle}>
                        <h4>{state.Diagramm1}</h4>
                        <h6>Nachrichten wurden bereits versendet.</h6>
                    </Card>
                    <Card style={writingStyle}>
                        <h4>{state.Diagramm2}</h4>
                        <h6>Einträge wurden bereits erstellt.</h6>
                    </Card>
                    <Card style={writingStyle}>
                        <h4>{state.Diagramm3}</h4>
                        <h6>registrierte User.</h6>
                    </Card>
                </CardGroup>
                <br/>
                <br/>
                <br/>
                <Card style={writingStyle}>
                    <Card.Text>Hier beschrieben wir eine schöne App für schöne Leute.</Card.Text>
                </Card>
            </> )
}
export default GuestSite