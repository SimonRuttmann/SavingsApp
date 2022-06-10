import {Card} from "react-bootstrap";
import {Bar} from "react-chartjs-2";
import React from "react";

export const Diagram3 = ({selectedGroup}) => {
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
                options={{ maintainAspectRatio: false }}
            />
        </Card>
    )
}