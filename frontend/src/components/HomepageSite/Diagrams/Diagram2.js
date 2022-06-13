// noinspection JSUnresolvedVariable

import {Card} from "react-bootstrap";
import {Line} from "react-chartjs-2";
import React from "react";
import {rainbow, sortByGivenDateType} from "../../../utils/util";

/**
 * diagramValues --> Januar         Februar         März
 *                   Kategorie123     Kategorie6      Kategorie26
 *                   Kategorie1532    Kategorie1      Kategorie2
 *                   Kategorie51      Kategorie3      Kategorie11
 */

export const Diagram2 = ({diagramValues,selectedCategories, defaultFilterInformation}) => {


    if(diagramValues == null || !Array.isArray(diagramValues)) return null;


    let arrayToSort = [...diagramValues];
    let sortedDiagramValues = arrayToSort.sort((a,b) => sortByGivenDateType(a,b,defaultFilterInformation.timeInterval));

    let labels = [];
    sortedDiagramValues.forEach(timeValue => {
        labels.push(timeValue.dateRepresentation)
    });

    let dataSet = [];

    let index = 1;
    for (let categoryToPopulate of selectedCategories) {

        let dataValues = populateCategory(categoryToPopulate)
        let label = categoryToPopulate.name

        dataSet.push( {
            label: label,
            data: dataValues,
            backgroundColor: rainbow(index, selectedCategories.length),
        })

        index ++;
    }


    function populateCategory(categoryToPopulate){

        let dataValues = [];

        for (let timeValue of sortedDiagramValues) {
            let value = getCategoryForTimeValue(categoryToPopulate, timeValue);
            dataValues.push(value);
        }

        return dataValues;
    }

    function getCategoryForTimeValue(categoryToPopulate, timeValue){
        for(let category of timeValue.values) {
            if (category.id === categoryToPopulate.id) {
                return category.sum;

            }
        }
        return 0;
    }


    return(
        <Card className="chartStyle">
            <Line data={{
                labels: labels,
                datasets: dataSet
            }}
                  options={{ maintainAspectRatio: false }}/>
        </Card>
    )
}