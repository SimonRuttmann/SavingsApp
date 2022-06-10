import axios from 'axios';

export function Get(url, auth){
    console.log("GET URL: ",url)
    console.log("Config :",auth)
    let response;
    if(auth == null){
        console.log("Running GET without auth")
        try {
            response = axios.get(url, null)
        } catch (e) {
            // catch error
            throw new Error(e.message)
        }
    }
    else {
        console.log("Running GET with auth")
        response = {
            method: 'get',
            url: 'http://localhost:8011/userservice/group',
            headers: {
                'Authorization': "Bearer " + auth
            }
        }
        response = axios(response)
    }
    return response
}

export function Post(url,body,config){
    let response;
    console.log("POST ",url)

    try {
        response = axios.post(url, body, config);
    } catch (e) {
        // catch error
        throw new Error(e.message)
    }

    return response
}

export function Put(url,body,config){
    let response;
    console.log("PUT ",url)

    try {
        response = axios.put(url, body, config);
    } catch (e) {
        // catch error
        throw new Error(e.message)
    }
    return response
}

export function Delete(url,config){
    let response;
    console.log("Delete ",url)

    try {
        response = axios.delete(url, config);
    } catch (e) {
        // catch error
        throw new Error(e.message)
    }
    return response
}
