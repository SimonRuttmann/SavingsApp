import React from "react";
import {Chart as ChartJS, ArcElement, Tooltip, Legend} from 'chart.js';
import "./styles.css"
import {Button, ButtonGroup, Card, CardGroup} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'

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

export default function GuestSite() {
    var AmountOfMessages = 15;
    var AmountOfEntries = 10;
    var AmountOfUsers = 33;
    return (
        <>
            <ButtonGroup style={buttonStyle}>
                <Button variant="secondary">Login</Button>
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
            <CardGroup>
                <Card>
                    <h5>{AmountOfMessages}</h5>
                </Card>
                <Card>
                    <h5>{AmountOfEntries}</h5>
                </Card>
                <Card>
                    <h5>{AmountOfUsers}</h5>
                </Card>
            </CardGroup>
            <br/>
            <Card style={writingStyle}>
                <Card.Text>Hier beschrieben wir eine schöne App für schöne Leute.</Card.Text>
            </Card>
        </>
    )
}