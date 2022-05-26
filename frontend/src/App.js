import React, {useState} from "react";
import Homepage from "./Homepage";
import GuestSite from "./GuestSite";
import {Route, Router} from "react-router-dom";

function App() {
  const [user, setUser] = useState('Robin Röcker')
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
  const [entrys, setEntrys] = useState([
    {
      id: 1,
      name: 'Whopper',
      costs: 12,
      user: 'Robin01',
      group: 'WG',
      timestamp: '01.03.2022'
    },
    {
      id: 2,
      name: 'Hund',
      costs: 400,
      user: 'Sargon',
      group: 'Ich',
      timestamp: '01.03.2022'
    },
    {
      id: 3,
      name: 'Stuhl',
      costs: 150,
      user: 'Michael123',
      group: 'WG',
      timestamp: '01.03.2022'
    },
    {
      id: 4,
      name: 'Nutella',
      costs: 10,
      user: 'Sargon',
      group: 'WG',
      timestamp: '01.03.2022'
    },
    {
      id: 5,
      name: 'Katze',
      costs: 460,
      user: 'Robin01',
      group: 'WG',
      timestamp: '01.03.2022'
    },
  ])

  const [newEntry, setNewEntry] = useState(    {
    id: 0,
    name: '',
    costs: 0,
    user: '',
    group: '',
    timestamp: ''
  })




  const [guestSite, setGuestSite] = useState(true)

  const AddGroup = ({group}) => {
    setGroups([...groups, group])
  }

  const DeleteGroup = (name) => {
    setGroups(groups.filter((group) => group.name !== name))
  }

  const AddEntry = (newEntry) => {
    setEntrys([...entrys, {id: entrys.length + 1, name: newEntry.name, costs: newEntry.costs, user: 'Robin01', group: 'WG', timestamp: newEntry.timestamp}])
  }

  const DeleteEntry = (id) => {
    setEntrys(entrys.filter((entry) => entry.id !== id))
  }

  const GetEntrys = (filterInfo) => {

  }

  return (
/*      <Router>
        <Route path="/" exact component={GuestSite(setGuestSite())}></Route>
        <Route path="/homepage" component={Homepage(groups)}/>
      </Router>*/
      <>
        {guestSite ? <GuestSite guestSite={guestSite} setGuestSite={setGuestSite}/> : <Homepage groups={groups} AddGroup={AddGroup} DeleteGroup={DeleteGroup} entrys={entrys} AddEntry={AddEntry} DeleteEntry={DeleteEntry} guestSite={guestSite} setGuestSite={setGuestSite}/>  }
      </>
  )
}

export default App;
