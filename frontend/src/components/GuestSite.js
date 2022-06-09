import React, {useEffect, useReducer} from "react";
import {Chart as ChartJS, ArcElement, Tooltip, Legend} from 'chart.js';
import {Button, Card, CardGroup, Container, Navbar} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import Gradient from 'rgt'
import '../styles.css'
import {useHistory} from "react-router-dom";
import axios from "axios";
import {AdvertisementServiceURL} from "../utils/constants";
import {updateAdvertismentData} from "../features/advertisment";
import keycloakService from "../api/auth.js";


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
let initalState = {
    isLoading : false,
    data : null,
    isError:false
};
const reducer = (state, action) => {
    switch (action.type){
        case getAdvertisementDataSuccess:
            return {
                ...state,
                isError: false,
                data: action.payload
            }
        case getAdvertisementDataError:
            return {
                ...state,
                isError: true,
                isLoading: false,
                data: mockData
            }
        default:
            return state;
    }
}

const mockData = {
    diagram1: '8946',
    diagram2: '5632',
    diagram3: '423'
}

const slogan = "Ein sauberer Haushalt benötigt ein sauberes Haushaltsbuch!"
const desc = "HaushaltsApp unterstützt Sie und Ihren Haushalt dabei einen Überblick über Ihre Finanzen zu behalten." +
    " Ihre Ausgaben mit Ihren Mitbewohnern und lassen Sie diese vollkommen kostenfrei analysieren!"

const getAdvertisementDataSuccess = "getAdvertisementDataSuccess";
const getAdvertisementDataError = "getAdvertisementDataError";

const GuestSite = () => {
    const [state, dispatch] = useReducer(reducer, initalState);
    console.log("Render", state)

    useEffect(() => {
            axios.get(AdvertisementServiceURL).then((response)=> {
                dispatch({type: getAdvertisementDataSuccess, payload: response.data})
                console.log("data", response.data)
            }).catch(dispatch({type:getAdvertisementDataError}))
    },[])

    const history = useHistory()

    const navToHomepage = () => {
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
                        {state.data == null ?<h2>Loading</h2>:<h2>{state.data.diagram1}</h2>}
                    </Gradient>
                    <h6>Nachrichten wurden bereits versendet.</h6>
                </Card>
                <Card className="writingStyle" style={writingStyle}>
                    <Gradient dir="top-to-bottom" from="#7928CA" to="#FF0080">
                        {state.data == null ?<h2>Loading</h2>:<h2>{state.data.diagram2}</h2>}
                    </Gradient>
                    <h6>Einträge wurden bereits erstellt.</h6>
                </Card>
                <Card className="writingStyle" style={writingStyle}>
                    <Gradient dir="top-to-bottom" from="#FF4D4D" to="#F9CB28">
                        {state.data == null ?
                            <h2>Loading</h2>: <h2>{state.data.diagram3}</h2>}
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