import axios from 'axios';

const debug = true;

export function Get(url, auth){

    let request;
    if(auth == null){
        try {
            request = axios.get(url, null)
        } catch (e) {
            // catch error
            throw new Error(e.message)
        }
    }
    else {

        request = {
            method: 'get',
            url: url,
            headers: {
                'Authorization': "Bearer " + auth
            }
        }
        request = axios(request)
    }
    if(debug) {
        request
            .catch((reason) => console.error("GET URL " + url + " failed with the following reason", reason))
    }
    return request
}

export function Post(url,body,auth){
    let request;

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
    if(debug) {
        request
            .catch((reason) => console.error("POST URL " + url + " failed with the following reason", reason))
    }
    return request
}

export function Put(url,body,auth){
    let request;

    try {
        request = {
            method: 'put',
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
    if(debug) {
        request
            .catch((reason) => console.error("PUT URL " + url + " failed with the following reason", reason))
    }
    return request
}

export function Delete(url, auth){
    let request;

    try {
        request = {
            method: 'delete',
            url: url,
            headers: {
                'Authorization': "Bearer " + auth
            }
        }

        request = axios(request)
    } catch (e) {
        // catch error
        throw new Error(e.message)
    }
    if(debug) {
        request
            .catch((reason) => console.error("DELETE URL " + url + " failed with the following reason", reason))
    }
    return request
}
