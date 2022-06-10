import React, {useEffect, useState} from "react";
import {
    ArcElement,
    BarElement,
    CategoryScale,
    Chart as ChartJS,
    Legend,
    LinearScale,
    LineElement,
    PointElement,
    Title,
    Tooltip
} from 'chart.js';
import {Bar, Line} from 'react-chartjs-2';
import "../css/styles.scss"
import "../css/homepage.scss"
import {
    Button,
    ButtonGroup,
    Card,
    CardGroup,
    Col,
    Container,
    Form,
    Nav,
    Navbar,
    NavDropdown,
    Row,
    Table
} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import {useHistory} from "react-router-dom";
import Chat from "./Chat";
import SettingsPopup from "./SettingsPopup";
import {useDispatch, useSelector} from "react-redux";
import {selectCategoryStore} from "../reduxStore/CategorySlice";
import {selectSavingEntryStore} from "../reduxStore/SavingEntrySlice";
import {selectGroupInformationStore} from "../reduxStore/GroupInformationSlice";
import {login, logout, selectUserStore} from "../reduxStore/UserSlice";
import KeyCloakService from "../api/Auth";
import Select from "react-select";
import makeAnimated from "react-select/animated";
const animatedComponents = makeAnimated();

ChartJS.register(ArcElement, Tooltip, Legend, CategoryScale, LinearScale, BarElement, Title, PointElement, LineElement);

const Homepage = ({groups, AddGroup, DeleteGroup, entrys, AddEntry, DeleteEntry, setGuestSite, user }) => {

    const debug = true;


    /** * * * * * * *
     ** Redux-Store *
     ** * * * * * * */
    const savingEntryStore      = useSelector(selectSavingEntryStore);
    const groupInformationStore = useSelector(selectGroupInformationStore);
    const userStore             = useSelector(selectUserStore);
    const categoryStore         = useSelector(selectCategoryStore);
    const dispatch = useDispatch()

    const mappedCategories = []/*categoryStore.map(category =>{
        return{  label: category.name, value: category.id}
    });*/

    useEffect( () => {
        dispatch(login(KeyCloakService.token));
        // eslint-disable-next-line react-hooks/exhaustive-deps
    },[])
    

    KeyCloakService.updateToken(5)
        .then((refreshed) => refreshToken(refreshed))
        .catch(function() {
            //TODO redirect to start page
            dispatch((logout()))
            console.log('Failed to refresh the token, or the session has expired');
        });

    const refreshToken = (refreshed) => {
        if (refreshed) {
            dispatch(login(KeyCloakService.token));
        } else {
            console.log('Token is still valid');
        }
    };
    
    
    if(debug) {
        console.log(savingEntryStore)
        console.log(groupInformationStore)
        console.log(userStore)
        console.log(categoryStore)

      //  dispatch(AddSavingEntry({id: 0, name: "Meine saving entry"}))
      //  savingEntryStore.forEach(savingEntry => console.log(savingEntry))
      //  console.log(userStore.name)
    }


    const getHeader = () => {

        let token;

        if(userStore == null || userStore.token == null) token = KeyCloakService.token;
        else token = userStore.token;

        return {
            headers: {
                Authorization: `Bearer ${token}`
            }
        };
    }


    /**
     * Local states
     */
    const [selectedGroup = groups[0], setSelectedGroup] = useState()
    const [selectedSettingsGroup = groups[0], setSelectedSettingsGroup] = useState()
    const [selectedEntry = entrys[0], setSelectedEntry] = useState()
    const [showMore, setShowMore] = useState(false)
    const [checked, setChecked] = useState(false);
    const [categories, setCategories] = useState([
        {
            name: 'Lernen',
            checked: false
        },
        {
            name: 'Food',
            checked: false
        },
        {
            name: 'Hund',
            checked: false
        },
        {
            name: 'Miete',
            checked: false
        }
    ])




    const history = useHistory()

    const navToGuestSite = () => {
        history.push("/");
    }


    return (
        <>
            <Navbar bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand>Haushalt</Navbar.Brand>
                    <Navbar.Toggle/>
                    <Navbar.Collapse className="justify-content-end">
                        <Nav className="me-auto">
                            <NavDropdown title="Ansicht" id="basic-nav-dropdown">
                                { groups.map(group => <NavDropdown.Item onClick={(e) => {
                                    e.preventDefault()
                                    setSelectedGroup(group)
                                }} href={group.name}>{group.name}</NavDropdown.Item>)}
                            </NavDropdown>
                            <Chat/>
                        </Nav>
                        <Button variant={"dark"} className="textStyle showSelectedGroup">{selectedGroup.name}</Button>
                        <SettingsPopup groups={ groups} setSelectedSettingsGroup={setSelectedSettingsGroup} selectedSettingsGroup={selectedSettingsGroup} AddGroup={AddGroup} DeleteGroup={DeleteGroup}/>
                        <Button variant="primary" className="buttonStyle" onClick={() => navToGuestSite()}>Logout</Button>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <CardGroup>
                <Card>
                    <Card.Body>
            <Form>
                <Row className="searchBar">
                    <Col>
                        <Form.Group>
                            <Form.Label>Name</Form.Label>
                            <Form.Control type="text" placeholder="Name eintragen" onChange={() => setSelectedEntry()} value={selectedEntry.name} />
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group>
                            <Form.Label>Kosten</Form.Label>
                            <Form.Control type="text" placeholder="Kosten eintragen" onChange={() => setSelectedEntry()} value={selectedEntry.costs} />
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group className="CategoryArea">
                            <Form.Label>Kategorien</Form.Label>
                            <div className="Multiselect">
                                <Select options={mappedCategories} components={animatedComponents}
                                        isMulti />
                            </div>
                        </Form.Group>
                    </Col>
                    <Col className="buttonCol">
                        <Form.Group className="buttonArea">
                            <Button onClick={() => AddEntry(selectedEntry)}>Eintrag erstellen</Button>
                            {!showMore &&  <Button variant="link" onClick={() => setShowMore(true)}>Zeig mehr</Button>}
                        </Form.Group>
                    </Col>
                </Row>
                <br/>
                { showMore &&
                <Row>
                    <Col>
                        <Form.Group>
                            <Form.Label>Datum</Form.Label>
                            <Form.Control type="text" placeholder="Datum eintragen" onChange={() => setSelectedEntry()} value={selectedEntry.timestamp}/>
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group>
                            <Form.Label>Beschreibung</Form.Label>
                            <Form.Control as="textarea" rows={3} onChange={() => setSelectedEntry()} />
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group>
                            <Button variant="link" onClick={() => setShowMore(false)}>Zeig weniger</Button>
                        </Form.Group>
                    </Col>
                </Row>}
            </Form>
                    </Card.Body>
                </Card>
            </CardGroup>
            <CardGroup>
                <Card className="chartStyle">
                    <Bar
                        data={{
                            // Name of the variables on x-axies for each bar
                            labels: ["1st bar", "2nd bar"],
                            datasets: [
                                {
                                    // Label for bars
                                    label: "total count/value",
                                    // Data or value of your each variable
                                    data: [selectedGroup.diagrams.diagram1.firstValue, selectedGroup.diagrams.diagram1.secondValue],
                                    // Color of each bar
                                    backgroundColor: ["aqua", "green"],
                                    // Border color of each bar
                                    borderColor: ["aqua", "green"],
                                    borderWidth: 0.5,
                                },
                            ],
                        }}
                        // Height of graph
                        height={400}
                        options={{
                            maintainAspectRatio: false,
                            scales: {
                                yAxes: [
                                    {
                                        ticks: {
                                            // The y-axis value will start from zero
                                            beginAtZero: true,
                                        },
                                    },
                                ],
                            },
                            legend: {
                                labels: {
                                    fontSize: 15,
                                },
                            },
                        }}
                        options={{ maintainAspectRatio: false }}
                    />
                </Card>
                <Card className="chartStyle">
                    <Line data={{
                        labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun"],
                        datasets: [
                            {
                                label: "First dataset",
                                data: selectedGroup.diagrams.diagram2.firstValues,
                                fill: true,
                                backgroundColor: "rgba(75,192,192,0.2)",
                                borderColor: "rgba(75,192,192,1)"
                            },
                            {
                                label: "Second dataset",
                                data: selectedGroup.diagrams.diagram2.secondValues,
                                fill: false,
                                borderColor: "#742774"
                            }
                        ]
                    }}
                          options={{ maintainAspectRatio: false }}/>
                </Card>
                <Card className="chartStyle">
                    <Bar
                        data={{
                            // Name of the variables on x-axies for each bar
                            labels: ["1st bar", "2nd bar"],
                            datasets: [
                                {
                                    // Label for bars
                                    label: "total count/value",
                                    // Data or value of your each variable
                                    data: [selectedGroup.diagrams.diagram3.firstValue, selectedGroup.diagrams.diagram3.firstValue],
                                    // Color of each bar
                                    backgroundColor: ["red", "yellow"],
                                    // Border color of each bar
                                    borderColor: ["red", "yellow"],
                                    borderWidth: 0.5,
                                },
                            ],
                        }}
                        // Height of graph
                        height={400}
                        options={{
                            maintainAspectRatio: false,
                            scales: {
                                yAxes: [
                                    {
                                        ticks: {
                                            // The y-axis value will start from zero
                                            beginAtZero: true,
                                        },
                                    },
                                ],
                            },
                            legend: {
                                labels: {
                                    fontSize: 15,
                                },
                            },
                        }}
                        options={{ maintainAspectRatio: false }}
                    />
                </Card>
            </CardGroup>
            <CardGroup>
                <Card>
                    <Card.Body>
                        <h4>Einträge</h4>
                        <br/>
                        <ButtonGroup className="buttonStyle">
                            <Button variant="secondary">Alle</Button>
                            <Button variant="secondary">WG</Button>
                            <Button variant="secondary">FAM</Button>
                            <Button variant="secondary">Ich</Button>
                        </ButtonGroup>
                        <ButtonGroup className="buttonStyle">
                            <Button onClick={() => DeleteEntry(selectedEntry.id)} variant="secondary">Eintrag löschen</Button>
                        </ButtonGroup>
                        <Table striped bordered hover>
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Name</th>
                                <th>Kosten</th>
                                <th>User</th>
                                <th>Gruppe</th>
                                <th>Zeitpunkt</th>
                            </tr>
                            </thead>
                            <tbody>
                            {entrys.map(entry =>
                                <tr onClick={() => setSelectedEntry(entry)}>
                                    <td>{entry.id}</td>
                                    <td>{entry.name}</td>
                                    <td>{entry.costs}</td>
                                    <td>{entry.user}</td>
                                    <td>{entry.group}</td>
                                    <td>{entry.timestamp}</td>
                                </tr>
                            )}
                            </tbody>
                        </Table>
                    </Card.Body>
                </Card>
            </CardGroup>

        </>
    )
}
export default Homepage