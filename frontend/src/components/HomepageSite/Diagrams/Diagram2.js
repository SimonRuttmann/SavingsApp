import {Card} from "react-bootstrap";
import {Line} from "react-chartjs-2";
import React from "react";

const colorArray = ["#4bc0c0","#742774","#456bf8","#765329","#0fff94"]

//selectedCategories and entryData are arrays
export const Diagram2 = ({entryData,selectedCategories}) => {
    let dataArray=[]

    selectedCategories.forEach((category,index) =>{
        const dataElement ={
            label: category.name,
            data: null, //todo how is entryData accessed and does it need sorting?,
            backgroundColor: colorArray[  (index+1) % colorArray.length],
        }
        dataArray.push(dataElement)
    })

    return(
        <Card className="chartStyle">
            <Line data={{
                //todo anhängig von gewähltem Zeitraum
                labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun"],
                datasets: dataArray
                /*datasets: [
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
                    }*/

            }}
                  options={{ maintainAspectRatio: false }}/>
        </Card>
    )
}