import {Card} from "react-bootstrap";
import {Bar} from "react-chartjs-2";
import React from "react";

export const Diagram1 = ({selectedGroup}) => {

    return (
        <Card className="chartStyle">
            <Bar
                data={{
                    // Name of the variables on x-axies for each bar
                    labels: ["Einnahmen vs Ausgaben"],
                    datasets: [
                        {
                            // Label for bars
                            label: "Einnahmen",
                            // Data or value of your each variable
                            data: [selectedGroup.diagrams.diagram1.firstValue],
                            // Color of each bar
                            backgroundColor: "green",
                            // Border color of each bar
                            borderColor: "green",
                            borderWidth: 0.5,
                        },
                        {
                            label: "Ausgaben",
                            backgroundColor: "red",
                            data: [selectedGroup.diagrams.diagram1.secondValue],
                            borderColor: "red",
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