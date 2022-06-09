import {Get} from "../Axios";

const AdvertisementServiceURL = "http://localhost:8010/global";

export default function fetchAdvertisement(){
    return Get(AdvertisementServiceURL)
}