import { useKeycloak } from "@react-keycloak/web";
import {Navigate} from "react-router-dom";

const PrivateRoute = ({ children }) => {
    const { keycloak } = useKeycloak();

    const isLoggedIn = keycloak.authenticated;
    console.log(isLoggedIn+"< is logged in | children  "+children)
    return isLoggedIn ? children : null


};

export default PrivateRoute;