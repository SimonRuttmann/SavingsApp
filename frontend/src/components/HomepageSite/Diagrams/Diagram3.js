// noinspection JSUnresolvedVariable

import {Card} from "react-bootstrap";
import {Line} from "react-chartjs-2";
import React from "react";
import {rainbow, sortByGivenDateType} from "../../../util";

export const Diagram3 = ({diagramValues, selectedUsers, defaultFilterInformation}) => {

    if(diagramValues == null || !Array.isArray(diagramValues)) return null;


    let arrayToSort = [...diagramValues];
    let sortedDiagramValues = arrayToSort.sort((a,b) => sortByGivenDateType(a,b,defaultFilterInformation.timeInterval));

    let labels = [];
    sortedDiagramValues.forEach(timeValue => {
        labels.push(timeValue.dateRepresentation)
    });

    let dataSet = [];

    let index = 1;
    for (let usersToPopulate of selectedUsers) {

        let dataValues = populateUser(usersToPopulate)
        let label = usersToPopulate.label ? usersToPopulate.label : usersToPopulate.name

        dataSet.push( {
            label: label,
            data: dataValues,
            backgroundColor: rainbow(index, selectedUsers.length),
        })

        index ++;
    }


    function populateUser(userToPopulate){

        let dataValues = [];

        for (let timeValue of sortedDiagramValues) {
            let value = getUserForTimeValue(userToPopulate, timeValue);
            dataValues.push(value);
        }

        return dataValues;
    }

    function getUserForTimeValue(userToPopulate, timeValue){
        for(let user of timeValue.values) {
            if (user.id === userToPopulate.id) {
                return user.sum;

            }
        }
        return 0;
    }

    return (
        <Card className="chartStyle">
            <Line data={{
                labels: labels,
                datasets: dataSet
            }}
                  options={{ maintainAspectRatio: false }}/>
        </Card>
    )
}