import axios from 'axios';

export function Get(url, config){
    let response;

    try {
        response = axios.get(url, config);
    } catch (e) {
        // catch error
        throw new Error(e.message)
    }
    return response
}

export function Post(url,body,config){
        let response;

        try {
            response = axios.post(url, body, config);
        } catch (e) {
            // catch error
            throw new Error(e.message)
        }
        return response
}

export function Put(){
    //todo
}

export function Delete(){
    //todo
}
