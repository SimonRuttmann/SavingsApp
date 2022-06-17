import {Get} from "../Axios";

const AdvertisementServiceURL = "http://localhost:8080/global";

export default function getAdvertisement(){
    return Get(AdvertisementServiceURL)
}