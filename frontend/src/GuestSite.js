import React, {useEffect} from "react";
import {Chart as ChartJS, ArcElement, Tooltip, Legend} from 'chart.js';
import {Button, Card, CardGroup, Container, Navbar} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import Gradient from 'rgt'
import './styles.css'


ChartJS.register(ArcElement, Tooltip, Legend);

const writingStyle = {
    textAlign: "center",
    padding: "1%",
    border: "0"
}

const buttonStyle = {
    float: "right",
    margin: "2px"
}

const statistics = {
    statistic1: '8946',
    statistic2: '5632',
    statistic3: '423'
}

const GuestSite = ({setGuestSite}) => {

    useEffect(() => {
        getStatisticsData()
    })

    const getStatisticsData = () => {
        fetch('http://localhost:8080/global')
            .then(response => response.json())
            .then(data => {
                statistics.statistic1 = data.Diagramm1
                statistics.statistic2 = data.Diagramm2
                statistics.statistic3 = data.Diagramm3
            })
    }
    return (
        <>
            <Navbar bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand>Haushalt</Navbar.Brand>
                    <Navbar.Toggle/>
                    <Navbar.Collapse className="justify-content-end">
                        <Button variant="light" style={buttonStyle} className="buttonStyle">Register</Button>
                        <Button variant="primary"  style={buttonStyle} className="buttonStyle"
                                onClick={() => setGuestSite(false)}>Login</Button>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <br/>
            <br/>
            <br/>
            <Card className="writingStyle" style={writingStyle}>
                <Card.Text>Hier steht ein Slogan für wundervolle Menschen.</Card.Text>
            </Card>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <CardGroup>
                <Card className="writingStyle" style={writingStyle}>
                    <Gradient dir="top-to-bottom" from="#007CF0" to="#00DFD8">
                        <h2>{statistics.statistic1}</h2>
                    </Gradient>
                    <h6>Nachrichten wurden bereits versendet.</h6>
                </Card>
                <Card className="writingStyle" style={writingStyle}>
                    <Gradient dir="top-to-bottom" from="#7928CA" to="#FF0080">
                        <h2>{statistics.statistic2}</h2>
                    </Gradient>
                    <h6>Einträge wurden bereits erstellt.</h6>
                </Card>
                <Card className="writingStyle" style={writingStyle}>
                    <Gradient dir="top-to-bottom" from="#FF4D4D" to="#F9CB28">
                        <h2>{statistics.statistic3}</h2>
                    </Gradient>
                    <h6>registrierte User.</h6>
                </Card>
            </CardGroup>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <Card className="writingStyle" style={writingStyle}>
                <Card.Text>Hier beschrieben wir eine schöne App für schöne Leute.</Card.Text>
            </Card>
            <br/>
            <br/>
            <br/>
        </>)
}
export default GuestSite