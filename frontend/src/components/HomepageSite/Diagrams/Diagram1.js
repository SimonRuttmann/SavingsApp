import {Card} from "react-bootstrap";
import {Bar} from "react-chartjs-2";
import React from "react";

export const Diagram1 = ({selectedGroup}) => {

    return (
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
                options={{ maintainAspectRatio: false }}

            />
        </Card>
    )
}