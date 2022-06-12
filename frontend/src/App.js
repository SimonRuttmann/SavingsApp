import React, {useState} from "react";
import Homepage from "./components/HomepageSite/Homepage";
import GuestSite from "./components/GuestSite";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import PrivateRoute from "./api/helper/PrivateRoute.js";
import {_kc} from "./api/Auth.js";
import { ReactKeycloakProvider } from "@react-keycloak/web";

function App() {

  const [groups, setGroups] = useState([
    {name: 'Ich',
      diagrams: {
        diagram1: {firstValue: 1552, secondValue: 1819},
        diagram2: {firstValues: [33, 25, 35, 51, 54, 75], secondValues: [25, 35, 35, 76, 25, 76]},
        diagram3: {firstValue: 613, secondValue: 1400}
      },
      members: ['Robin']
    },
    {name: 'WG',
      diagrams: {
        diagram1: {firstValue: 1052, secondValue: 1019},
        diagram2: {firstValues: [31, 88, 14, 45, 54, 66], secondValues: [33, 25, 35, 51, 54, 76]},
        diagram3: {firstValue: 1000, secondValue: 1000}
      },
      members: ['Robin', 'Max', 'Leon']
    },
    {name: 'Familie',
      diagrams: {
        diagram1: {firstValue: 1852, secondValue: 1219},
        diagram2: {firstValues: [11, 22, 33, 44, 55, 66], secondValues: [66, 55, 44, 33, 22, 11]},
        diagram3: {firstValue: 1813, secondValue: 110}
      },
      members: ['Robin', 'Ralf', 'Maria']
    },
  ])


  const AddGroup = ({group}) => {
    setGroups([...groups, group])
  }

  const DeleteGroup = (name) => {
    setGroups(groups.filter((group) => group.name !== name))
  }



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
                <Homepage groups={groups}
                          AddGroup={AddGroup}
                          DeleteGroup={DeleteGroup}
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
