
const allMonths = ['Januar','Februar','März', 'April','Mai','Juni','Juli','August','September','Oktober','November','Dezember'];


export function sortByGivenDateType(a, b, type){

    try {
        switch (type) {
            case "Day":
                return parseDate(a.dateRepresentation) - parseDate(b.dateRepresentation);
            case "Year":
                return a.dateRepresentation - b.dateRepresentation;
            case "Week":
            case "Month":

                let aDate = a.dateRepresentation.split(".")
                let aPrimaryType = aDate[0]
                let aYear = aDate[1]

                let bDate = b.dateRepresentation.split(".")
                let bPrimaryType = bDate[0]
                let bYear = bDate[1]

                let compareByYear = aYear - bYear;
                if(compareByYear !== 0) return compareByYear;

                if(type === "Week") return aPrimaryType - bPrimaryType;
                if(type === "Month") return allMonths.indexOf(aPrimaryType) - allMonths.indexOf(bPrimaryType);
                return 0;
        }
    }
    catch(e){
        return 0;
    }
}

function parseDate(input) {
    let parts = input.match(/(\d+)/g);
    // note parts[1]-1
    return new Date(parts[2], parts[1]-1, parts[0]);
}



/**
 * Copied from StackOverflow
 * @param numOfSteps: Total number steps to get color, means total colors
 * @param step: The step number, means the order of the color
 */
export function rainbow(numOfSteps, step) {
    let r, g, b;
    let h = step / numOfSteps;
    let i = ~~(h * 6);
    let f = h * 6 - i;
    let q = 1 - f;
    // eslint-disable-next-line default-case
    switch(i % 6){
        case 0: r = 1; g = f; b = 0; break;
        case 1: r = q; g = 1; b = 0; break;
        case 2: r = 0; g = 1; b = f; break;
        case 3: r = 0; g = q; b = 1; break;
        case 4: r = f; g = 0; b = 1; break;
        case 5: r = 1; g = 0; b = q; break;
    }
    var c = "#" + ("00" + (~ ~(r * 255)).toString(16)).slice(-2) + ("00" + (~ ~(g * 255)).toString(16)).slice(-2) + ("00" + (~ ~(b * 255)).toString(16)).slice(-2);
    return (c);
}

const regexp = /^-?(\d{0,8})(\.)?(\d{1,2})?$/;
export function isNumberOrDecimalString(str) {
    return regexp.test(str)
}