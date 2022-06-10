import axios from 'axios';
import UserService from "./Auth";

export function Get(url, auth){
    console.log("GET URL: ",url)
    let request;
    if(auth == null){
        console.log("Running GET without auth")
        try {
            request = axios.get(url, null)
        } catch (e) {
            // catch error
            throw new Error(e.message)
        }
    }
    else {
        console.log("Running GET with auth")
        request = {
            method: 'get',
            url: url,
            headers: {
                'Authorization': "Bearer " + auth
            }
        }
        request = axios(request)
    }
    request.then((value => console.log("GET URL "+url+" worked, it returned ",value)))
    return request
}

export function Post(url,body,auth){
    let request;
    console.log("POST ",url)
    console.log("POST BODY ",body)
    try {
        request = {
            method: 'post',
            url: url,
            headers: {
                'Authorization': "Bearer " + auth
            },
            data:body
        }

        request = axios(request)
    } catch (e) {
        // catch error
        throw new Error(e.message)
    }
    request.then((value => console.log("POST URL "+url+" worked, it returned ",value)))
    return request
}

export function Put(url,body,auth){
    let request;
    console.log("PUT ",url)

    try {
        request = {
            method: 'post',
            url: url,
            headers: {
                'Authorization': "Bearer " + auth
            },
            data:body
        }
    } catch (e) {
        // catch error
        throw new Error(e.message)
    }
    request.then((value => console.log("PUT URL "+url+" worked, it returned ",value)))
    return request
}

export function Delete(url, auth){
    let request;
    console.log("Delete ",url)

    try {
        request = {
            method: 'post',
            url: url,
            headers: {
                'Authorization': "Bearer " + auth
            }
        }
    } catch (e) {
        // catch error
        throw new Error(e.message)
    }
    return request
}
