import React from "react";
import {Chart as ChartJS, ArcElement, Tooltip, Legend} from 'chart.js';
import {Pie} from 'react-chartjs-2';
import "./styles.css"
import {Button, ButtonGroup, Card, CardGroup} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'

ChartJS.register(ArcElement, Tooltip, Legend);

export const data1 = {
    labels: ['first cost', 'second cost', 'third cost', 'fourth cost'],
    datasets: [
        {
            label: '# of costs',
            data: [12, 19, 3, 5],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
            ],
            borderWidth: 1,
        },
    ],
};
export const data2 = {
    labels: ['first income', 'second income', 'third income'],
    datasets: [
        {
            label: '# of income',
            data: [40, 40, 20],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
            ],
            borderWidth: 1,
        },
    ],
};
export const data3 = {
    labels: ['first saving', 'second saving', 'third saving', 'fourth saving', 'sixt saving', 'seventh saving'],
    datasets: [
        {
            label: '# of saving',
            data: [12, 19, 3, 5, 2, 3],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)',
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)',
            ],
            borderWidth: 1,
        },
    ],
};

const chartStyle = {
    border: "0",
    padding: "2%",
};

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
                <Card style={chartStyle}>
                    <Pie data={data1}></Pie>
                </Card>
                <Card style={chartStyle}>
                    <Pie data={data2}></Pie>
                </Card>
                <Card style={chartStyle}>
                    <Pie data={data3}></Pie>
                </Card>
            </CardGroup>
            <br/>
            <Card style={writingStyle}>
                <Card.Text>Hier beschrieben wir eine schöne App für schöne Leute.</Card.Text>
            </Card>
        </>
    )
}