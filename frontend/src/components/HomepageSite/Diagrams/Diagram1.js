import {Card} from "react-bootstrap";
import {Bar} from "react-chartjs-2";
import React from "react";

export const Diagram1 = ({diagramValues}) => {

    return (
        <Card className="chartStyle">
            <Bar
                data={{
                    // Name of the variables on x-axies for each bar
                    labels: ["Einnahmen", "Ausgaben", "Balance", "Zukünftige Balance"],
                    datasets: [
                        {
                            // Label for bars
                            label: "total count/value",
                            // Data or value of your each variable
                            data: [diagramValues.income, -diagramValues.outcome, diagramValues.balance, diagramValues.futureBalance],
                            // Color of each bar
                            backgroundColor: ["green", "red", "yellow", "orange"],
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