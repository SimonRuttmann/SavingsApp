import React from "react";
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
import {Button, ButtonGroup, Card, CardGroup, Container, Nav, Navbar, NavDropdown, Table} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'

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

export default function Homepage() {
    return (
        <>
            <Navbar bg="light" expand="lg">
                <Container>
                    <Navbar.Brand href="start">Start</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link >Allgemein</Nav.Link>
                            <NavDropdown title="Ansicht" id="basic-nav-dropdown">
                                <NavDropdown.Item href="Me">Ich</NavDropdown.Item>
                                <NavDropdown.Item href="WG">WG</NavDropdown.Item>
                                <NavDropdown.Item href="Fam">Fam</NavDropdown.Item>
                            </NavDropdown>
                            <Nav.Link href="Settings">Settings</Nav.Link>
                        </Nav>
                        <Button variant="secondary">Logout</Button>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
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
                                    data: [1552, 1319],
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
                                data: [33, 53, 85, 41, 44, 65],
                                fill: true,
                                backgroundColor: "rgba(75,192,192,0.2)",
                                borderColor: "rgba(75,192,192,1)"
                            },
                            {
                                label: "Second dataset",
                                data: [33, 25, 35, 51, 54, 76],
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
                                    data: [613, 1400],
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
            <br/>
            <ButtonGroup style={buttonStyle}>
                <Button variant="secondary">Alle</Button>
                <Button variant="secondary">WG</Button>
                <Button variant="secondary">FAM</Button>
                <Button variant="secondary">Ich</Button>
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
                <tr>
                    <td>1</td>
                    <td>Robin</td>
                    <td>200</td>
                    <td>Robin01</td>
                    <td>WG</td>
                    <td>01.03.2022</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>Simon</td>
                    <td>400</td>
                    <td>Sargon</td>
                    <td>WG</td>
                    <td>10.02.2022</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>Michael</td>
                    <td>150</td>
                    <td>Michael245</td>
                    <td>WG</td>
                    <td>09.02.2022</td>
                </tr>
                </tbody>
            </Table>

        </>
    )
}