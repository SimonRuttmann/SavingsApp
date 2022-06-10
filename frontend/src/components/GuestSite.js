import React, {useEffect, useReducer} from "react";
import {Chart as ChartJS, ArcElement, Tooltip, Legend} from 'chart.js';
import {Button, Card, CardGroup, Container, Navbar} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import '../css/styles.scss'
import '../css/guestsite.scss'
import keycloakService from "../api/Auth.js";
import getAdvertisement from "../api/services/Advertisement";
import {useHistory} from "react-router-dom";
import keycloak from "../api/Auth.js";

ChartJS.register(ArcElement, Tooltip, Legend);

const slogan = "Ein sauberer Haushalt benötigt ein sauberes Haushaltsbuch!"
const desc =   "HaushaltsApp unterstützt Sie und Ihren Haushalt dabei einen Überblick über Ihre Finanzen zu behalten. " +
               "Teilen Sie Ihre Ausgaben mit Ihren Mitbewohnern und lassen Sie diese vollkommen kostenfrei analysieren!"

//Reducer action types
const getAdvertisementDataSuccess = "getAdvertisementDataSuccess";
const getAdvertisementDataError = "getAdvertisementDataError";

let initialAdvertisementState = {
    isLoading : false,
    data : null,
    isError:false
};
const advertisementReducer = (state, action) => {
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
    const [advertisementState, dispatchAdvertisement] = useReducer(advertisementReducer, initialAdvertisementState);
    //console.log("Render", state)

    useEffect(() => {
        console.log("erster Redirekt")
            Redirect()
            getAdvertisement().then((response)=> {
                dispatchAdvertisement({type: getAdvertisementDataSuccess, payload: response.data})
                console.log("data", response.data)
            }).catch(dispatchAdvertisement({type:getAdvertisementDataError}))
    },[])



    //
    const history = useHistory();
    function handleClick() {

        history.push('/homepage');

    }
    function Redirect() {
         if (keycloak.isLoggedIn()) {
            history.push("/homepage")
        }
    }


    return (
        <>
            <Navbar bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand>Haushalt</Navbar.Brand>
                    <Navbar.Toggle/>
                    <Navbar.Collapse className="justify-content-end">
                        <Button variant="light" onClick={() => keycloakService.doRegister()}>Register</Button>
                        <Button variant="primary" onClick={() => handleClick() }>Login(oneAnmeldung)</Button>
                        <Button variant="primary" onClick={() => keycloakService.doLogin()}>Login(mitAnmeldung)</Button>
                        <Button variant="light" onClick={() => keycloakService.doLogout()}>Logout(temp)</Button>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <Card className="slogen">
                <h2 className="textColorfull"> {slogan}</h2>
            </Card>
            <CardGroup className="advertisementGroup">
                <Card className="writingStyle">
                    <h2  className="textDiagramm1">{advertisementState.data == null ?"Loading":advertisementState.data.diagram1}</h2>
                    <h6>Nachrichten wurden bereits versendet.</h6>
                </Card>
                <Card className="writingStyle">
                    <h2  className="textDiagramm2">{advertisementState.data == null ?"Loading":advertisementState.data.diagram2}</h2>
                    <h6>Einträge wurden bereits erstellt.</h6>
                </Card>
                <Card className="writingStyle">
                    <h2  className="textDiagramm3">{advertisementState.data == null ?"Loading":advertisementState.data.diagram3}</h2>
                    <h6>registrierte User.</h6>
                </Card>
            </CardGroup>
            <Card className="writingStyle">
                <Card.Text>{desc}</Card.Text>
            </Card>
        </>)
}
export default GuestSite