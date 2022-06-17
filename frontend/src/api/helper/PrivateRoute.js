import keycloak from "../Auth"
import {useHistory} from "react-router-dom";

const PrivateRoute = ({ children }) => {
    const history = useHistory();

    const isLoggedIn = keycloak.isLoggedIn()
    return isLoggedIn ? children : history.push("/")


};

export default PrivateRoute;