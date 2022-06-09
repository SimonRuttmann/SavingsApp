import React, {useEffect} from "react";
import {Chart as ChartJS, ArcElement, Tooltip, Legend} from 'chart.js';
import {Button, Card, CardGroup, Container, Navbar} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import Gradient from 'rgt'
import '../styles.css'
import {useHistory} from "react-router-dom";
import {useDispatch} from 'react-redux'
import { login } from '../features/user'
import { logout } from '../features/user'
import {update, updateAdvertismentData} from "../features/advertisment";
import keycloakService from "../api/auth.js";
import keycloak from "../api/auth.js";


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

const slogan = "Ein sauberer Haushalt benötigt ein sauberes Haushaltsbuch!"
const desc = "HaushaltsApp unterstützt Sie und Ihren Haushalt dabei einen Überblick über Ihre Finanzen zu behalten." +
    " Ihre Ausgaben mit Ihren Mitbewohnern und lassen Sie diese vollkommen kostenfrei analysieren!"
const GuestSite = () => {

    useEffect(() => {
        getStatisticsData()
    })

    const dispatch = useDispatch()

    const history = useHistory()

    const navToHomepage = () => {
    }




    const getStatisticsData = () => {
        fetch('http://localhost:8010/global')
            .then(response => response.json())
            .then(data => {
                dispatch(updateAdvertismentData(data))
            })
    }
    return (
        <>
            <Navbar bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand>Haushalt</Navbar.Brand>
                    <Navbar.Toggle/>
                    <Navbar.Collapse className="justify-content-end">
                        <Button variant="light" style={buttonStyle} className="buttonStyle" onClick={() => keycloakService.register()}>Register</Button>
                        <Button variant="primary"  style={buttonStyle} className="buttonStyle" onClick={() => keycloakService.login() }>Login</Button>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <br/>
            <br/>
            <br/>
            <Card className="writingStyle" style={writingStyle}>
                <Card.Text>{slogan}</Card.Text>
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
                <Card.Text>{desc}</Card.Text>
            </Card>
            <br/>
            <br/>
            <br/>
        </>)
}
export default GuestSite