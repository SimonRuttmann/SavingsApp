import React, {useState} from "react";
import Homepage from "./components/HomepageSite/Homepage";
import GuestSite from "./components/GuestSite";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import PrivateRoute from "./api/helper/PrivateRoute.js";
import {_kc} from "./api/Auth.js";
import { ReactKeycloakProvider } from "@react-keycloak/web";

function App() {

  const [getActiveGroupId, setActiveGroupId] = useState(null);

  return (
      <div>
        <ReactKeycloakProvider authClient={_kc}>
        <BrowserRouter>
        <Switch>
            <Route exact path="/">
              <GuestSite/>
            </Route>
            <PrivateRoute exact path="/homepage" >
                <Homepage
                          getActiveGroupId={getActiveGroupId}
                          setActiveGroupId={setActiveGroupId}/>
            </PrivateRoute>
        </Switch>
        </BrowserRouter>
        </ReactKeycloakProvider>
      </div>
  )
}

export default App;
