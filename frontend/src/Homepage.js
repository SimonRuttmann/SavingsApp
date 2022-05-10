import React, {useState} from "react";
import {
    Chart as ChartJS,
    ArcElement,
    Tooltip,
    Legend,
    CategoryScale,
    LinearScale,
    BarElement,
    Title, PointElement, LineElement
} from 'chart.js';
import {Bar, Line} from 'react-chartjs-2';
import "./styles.css"
import {Button, ButtonGroup, Card, CardGroup, Container, Form, Nav, Navbar, NavDropdown, Table} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import Popup from "./Popup";
ChartJS.register(ArcElement, Tooltip, Legend, CategoryScale, LinearScale, BarElement, Title, PointElement, LineElement);

const chartStyle = {
    border: "0",
    padding: "3%",
    height: "500px",
    width: "400px",
};

const buttonStyle = {
    float: "right",
    paddingRight: "1%",
}

const textstyle = {
    textAlign: "center",
    paddingRight: "45%",
    paddingTop: "1%",
    border: "0"
}



const Homepage = ({groups, AddGroup, DeleteGroup, entrys, AddEntry, DeleteEntry, newEntry, setGuestSite }) => {

    const [selectedGroup = groups[0], setSelectedGroup] = useState()
    const [selectedSettingsGroup = groups[0], setSelectedSettingsGroup] = useState()
    const [selectedEntry = entrys[0], setSelectedEntry] = useState()

    const [isOpen, setIsOpen] = useState(false);

    const togglePopup = () => {
        setIsOpen(!isOpen);
    }

    return (
        <>
            <Navbar bg="light" expand="lg">
                <Container>
                    <Navbar.Brand href="start">Start</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <NavDropdown title="Ansicht" id="basic-nav-dropdown">
                                { groups.map(group => <NavDropdown.Item onClick={(e) => {
                                    e.preventDefault()
                                    setSelectedGroup(group)
                                }} href={group.name}>{group.name}</NavDropdown.Item>)}
                            </NavDropdown>
                        </Nav>
                        <h5 style={textstyle}>{selectedGroup.name}</h5>
                        <Button type="button" onClick={togglePopup} variant="secondary">Settings</Button>
                        <Button variant="secondary" onClick={() => setGuestSite(true)}>Logout</Button>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <form>
                <CardGroup >
                    <Card>
                        <label>Name:</label>
                        <input onChange={() => setSelectedEntry()} value={selectedEntry.name}/>
                    </Card>
                    <Card>
                        <label>Kosten</label>
                        <input onChange={() => setSelectedEntry()} value={selectedEntry.costs}/>
                    </Card>
                    <Card>
                        <label>Kategorie</label>
                        <input onChange={() => setSelectedEntry()}/>
                    </Card>
                    <Card>
                        <Button onClick={() => AddEntry(selectedEntry)} variant="secondary">Eintrag erstellen</Button>
                    </Card>
                </CardGroup>
                <CardGroup>
                    <Card>
                        <label>Datum</label>
                        <input onChange={() => setSelectedEntry()} value={selectedEntry.timestamp}/>
                    </Card>
                    <Card>
                        <label>Beschreibung</label>
                        <input onChange={() => setSelectedEntry()}/>
                    </Card>
                </CardGroup>
            </form>
            <CardGroup>
                <Card style={chartStyle}>
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
                <Card style={chartStyle}>
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
                <Card style={chartStyle}>
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
            {isOpen && <Popup
                content={<>
                    <h2>Settings</h2>
                    <h5>Gruppen:</h5>
                    { groups.map(group => <h6 onClick={(e) => {
                        e.preventDefault()
                        setSelectedSettingsGroup(group)
                    }} href={group.name}>{group.name}</h6>)}
                    <h5>Mitglieder:</h5>
                    {selectedSettingsGroup.members.map(user => <h6>{user}</h6>)}
                    <Button onClick={() => AddGroup({name: 'Familie2',
                        diagrams: {
                        diagram1: {firstValue: 1852, secondValue: 1219},
                        diagram2: {firstValues: [11, 22, 33, 44, 55, 66], secondValues: [66, 55, 44, 33, 22, 11]},
                        diagram3: {firstValue: 1813, secondValue: 110}
                    },
                        members: ['Robin', 'Ralf', 'Maria']
                    })} variant="secondary">Gruppe hinzufügen</Button>
                    <Button onClick={ () => DeleteGroup(selectedSettingsGroup.name)} variant="secondary">Gruppe Verlassen</Button>
                </>}
                handleClose={togglePopup}
            />}
            <br/>
            <ButtonGroup style={buttonStyle}>
                <Button variant="secondary">Alle</Button>
                <Button variant="secondary">WG</Button>
                <Button variant="secondary">FAM</Button>
                <Button variant="secondary">Ich</Button>
            </ButtonGroup>
            <ButtonGroup style={buttonStyle}>
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

        </>
    )
}
export default Homepage