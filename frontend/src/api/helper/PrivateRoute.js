import { useKeycloak } from "@react-keycloak/web";
import keycloak from "../Auth"
import {useHistory} from "react-router-dom";

const PrivateRoute = ({ children }) => {
    const history = useHistory();

    const isLoggedIn = keycloak.isLoggedIn()
    console.log(isLoggedIn+"< is logged in | children  "+children)
    return isLoggedIn ? children : history.push("/")


};

export default PrivateRoute;