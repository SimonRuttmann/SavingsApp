import React, {useEffect, useReducer} from "react";
import {Chart as ChartJS, ArcElement, Tooltip, Legend} from 'chart.js';
import {Button, Card, CardGroup, Container, Navbar} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import '../styles.css'
import keycloakService from "../api/Auth.js";
import fetchAdvertisement from "../api/services/Advertisement";

ChartJS.register(ArcElement, Tooltip, Legend);

const slogan = "Ein sauberer Haushalt benötigt ein sauberes Haushaltsbuch!"
const desc = "HaushaltsApp unterstützt Sie und Ihren Haushalt dabei einen Überblick über Ihre Finanzen zu behalten. " +
    "Teilen Sie Ihre Ausgaben mit Ihren Mitbewohnern und lassen Sie diese vollkommen kostenfrei analysieren!"

//Reducer action types
const getAdvertisementDataSuccess = "getAdvertisementDataSuccess";
const getAdvertisementDataError = "getAdvertisementDataError";

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

//Is inserted into state if new data can't be pulled
const mockData = {
    diagram1: '8946',
    diagram2: '5632',
    diagram3: '423'
}

const GuestSite = () => {
    const [state, dispatch] = useReducer(reducer, initalState);
    //console.log("Render", state)

    useEffect(() => {
            fetchAdvertisement().then((response)=> {
                dispatch({type: getAdvertisementDataSuccess, payload: response.data})
                console.log("data", response.data)
            }).catch(dispatch({type:getAdvertisementDataError}))
    },[])

    return (
        <>
            <Navbar bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand>Haushalt</Navbar.Brand>
                    <Navbar.Toggle/>
                    <Navbar.Collapse className="justify-content-end">
                        <Button variant="light" onClick={() => keycloakService.register()}>Register</Button>
                        <Button variant="primary" onClick={() => keycloakService.login() }>Login</Button>
                        <Button variant="light" onClick={() => keycloakService.logout()}>Logout(temp)</Button>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <Card className="slogen">
                <h2 className="textColorfull"> {slogan}</h2>
            </Card>
            <CardGroup className="advertisementGroup">
                <Card className="writingStyle">
                    <h2  className="textDiagramm1">{state.data == null ?"Loading":state.data.diagram1}</h2>
                    <h6>Nachrichten wurden bereits versendet.</h6>
                </Card>
                <Card className="writingStyle">
                    <h2  className="textDiagramm2">{state.data == null ?"Loading":state.data.diagram2}</h2>
                    <h6>Einträge wurden bereits erstellt.</h6>
                </Card>
                <Card className="writingStyle">
                    <h2  className="textDiagramm3">{state.data == null ?"Loading":state.data.diagram3}</h2>
                    <h6>registrierte User.</h6>
                </Card>
            </CardGroup>
            <Card className="writingStyle">
                <Card.Text>{desc}</Card.Text>
            </Card>
        </>)
}
export default GuestSite