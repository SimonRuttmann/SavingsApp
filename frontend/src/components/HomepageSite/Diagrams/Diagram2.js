import {Card} from "react-bootstrap";
import {Line} from "react-chartjs-2";
import React from "react";

export const Diagram2 = ({selectedGroup}) => {

    return(
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
    )
}