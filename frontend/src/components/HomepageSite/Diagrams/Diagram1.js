import {Card} from "react-bootstrap";
import {Bar} from "react-chartjs-2";
import React from "react";

export const Diagram1 = ({diagramValues}) => {

    return (
        <Card className="chartStyle">
            <Bar
                data={{
                    // Name of the variables on x-axies for each bar
                    labels: ["Datenvergleich"],
                    datasets: [
                        {
                            // Label for bars
                            label: "Einnahmen",
                            // Data or value of your each variable
                            data: [diagramValues.income],
                            // Color of each bar
                            backgroundColor: "green",
                            // Border color of each bar
                            borderColor: "gray",
                            borderWidth: 0.5,
                        },
                        {
                            label: "Ausgaben",
                            // Data or value of your each variable
                            data: [-diagramValues.outcome],
                            // Color of each bar
                            backgroundColor: "red",
                            // Border color of each bar
                            borderColor: "gray",
                            borderWidth: 0.5,
                        },
                        {
                            label: "Balance",
                            // Data or value of your each variable
                            data: [diagramValues.balance],
                            // Color of each bar
                            backgroundColor: "yellow",
                            // Border color of each bar
                            borderColor: "gray",
                            borderWidth: 0.5
                        },
                        {
                            label: "Zukünftige Balance",
                            // Data or value of your each variable
                            data: [diagramValues.futureBalance],
                            // Color of each bar
                            backgroundColor: "orange",
                            // Border color of each bar
                            borderColor: "gray",
                            borderWidth: 0.5
                        }
                        ],
                }}
                // Height of graph
                height={400}
                options={{ maintainAspectRatio: false }}

            />
        </Card>
    )
}