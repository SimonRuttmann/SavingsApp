import React, {useEffect, useState} from "react";
import {ArcElement, BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, LineElement, PointElement, Title, Tooltip} from 'chart.js';
import {Bar, Line} from 'react-chartjs-2';
import "../css/styles.scss"
import "../css/homepage.scss"
import {Button, ButtonGroup, Card, CardGroup, Col, Container, Form, Nav, Navbar, NavDropdown, Row, Table} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import {useHistory} from "react-router-dom";
import Chat from "./Chat";
import SettingsPopup from "./SettingsPopup";
import {useDispatch, useSelector} from "react-redux";
import {fetchCategoriesFromServer, selectCategoryStore} from "../reduxStore/CategorySlice";
import {fetchSavingEntriesFromServer, selectSavingEntryStore} from "../reduxStore/SavingEntrySlice";
import {fetchGeneralInformationToGroupFromServer, fetchGroupCoreInformationFromServer, selectGroupInformationStore} from "../reduxStore/GroupInformationSlice";
import {login, logout, selectUserStore} from "../reduxStore/UserSlice";
import KeyCloakService from "../api/Auth";
import Select from "react-select";
import makeAnimated from "react-select/animated";
import {fetchProcessingResultsFromServer, selectProcessingStore} from "../reduxStore/ProcessingSlice";
import {Diagram1} from "./Diagrams/Diagram1";
import {Diagram2} from "./Diagrams/Diagram2";
import {Diagram3} from "./Diagrams/Diagram3";
import {EntryTable} from "./EntryTable";
import {NavigationBar} from "./NavigationBar";
const animatedComponents = makeAnimated();

ChartJS.register(ArcElement, Tooltip, Legend, CategoryScale, LinearScale, BarElement, Title, PointElement, LineElement);


const Homepage = ({groups, AddGroup, DeleteGroup, entrys, AddEntry, DeleteEntry, setGuestSite, user }) => {

    const debug = false;


    /** * * * * * * *
     ** Redux-Store *
     ** * * * * * * */
    const savingEntryStore      = useSelector(selectSavingEntryStore);
    const groupInformationStore = useSelector(selectGroupInformationStore);
    const userStore             = useSelector(selectUserStore);
    const categoryStore         = useSelector(selectCategoryStore);
    const processingStore       = useSelector(selectProcessingStore);
    const dispatch = useDispatch()

    const mappedCategories = categoryStore.map(category =>{
        return{  label: category.name, value: category.id}
    });

    const defaultFilterInformation = {
        "sortParameter": "CreationDate",
        "timeInterval": "Day",
        "startDate": null,
        "endDate": null,
        "personIds": [],
        "categoryIds": []
    }

    useEffect( () => {
        dispatch(login(KeyCloakService.token));

        dispatch(fetchGroupCoreInformationFromServer(getHeader()))
            .then(() => fetchContentInformation());

        // eslint-disable-next-line react-hooks/exhaustive-deps
    },[])
    

    const fetchContentInformation = () => {

        //Get personal group
        let personGroup = groupInformationStore.find(group => group.personGroup === true);

        //Fetch all general information about the groups
        for (let group of groupInformationStore){
            dispatch(fetchGeneralInformationToGroupFromServer(getHeader(), group.id))
        }

        //fetch categories for this group
        dispatch(fetchCategoriesFromServer(getHeader(), personGroup.id));
        //fetch saving entries for this group
        dispatch(fetchSavingEntriesFromServer(getHeader(), personGroup.id));
        //fetch processing results
        dispatch(fetchProcessingResultsFromServer(getHeader(), personGroup.id, defaultFilterInformation))

    }

    KeyCloakService.updateToken(5)
        .then((refreshed) => refreshToken(refreshed))
        .catch(function() {
            //TODO redirect to start page
            dispatch((logout()))
            console.log('Failed to refresh the token, or the session has expired');
        });

    const refreshToken = (refreshed) => {
        if (refreshed) {
            dispatch(login(KeyCloakService.token));
        } else {
            console.log('Token is still valid');
        }
    };
    
    
    if(debug) {
        console.log(savingEntryStore)
        console.log(groupInformationStore)
        console.log(userStore)
        console.log(categoryStore)

      //  dispatch(AddSavingEntry({id: 0, name: "Meine saving entry"}))
      //  savingEntryStore.forEach(savingEntry => console.log(savingEntry))
      //  console.log(userStore.name)
    }


    const getHeader = () => {
        let token;

        if(userStore == null || userStore.token == null) token = KeyCloakService.token;
        else token = userStore.token;

        return {
            headers: {
                Authorization: `Bearer ${token}`
            }
        };
    }


    /**
     * Local states
     */
    const [selectedGroup = groups[0], setSelectedGroup] = useState()
    const [selectedSettingsGroup = groups[0], setSelectedSettingsGroup] = useState()
    const [selectedEntry = entrys[0], setSelectedEntry] = useState()
    const [showMore, setShowMore] = useState(false)

    const [selectedCategories, setSelectedCategories] = useState([])


    const history = useHistory()

    const navToGuestSite = () => {
        history.push("/");
    }
    console.log("group")
console.log(groups)
    return (
        <>
             <NavigationBar groups={groups}
                            setSelectedGroup={setSelectedGroup}
                            selectedGroup={selectedGroup}
                            selectedSettingsGroup={selectedSettingsGroup}
                            AddGroup={AddGroup}
                            DeleteGroup={DeleteGroup}
                            navToGuestSite={navToGuestSite}
                            />

            <CardGroup>
                <Card>
                    <Card.Body>
            <Form>
                <Row className="searchBar">
                    <Col>
                        <Form.Group>
                            <Form.Label>Name</Form.Label>
                            <Form.Control type="text" placeholder="Name eintragen" onChange={() => setSelectedEntry()} value={selectedEntry.name} />
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group>
                            <Form.Label>Kosten</Form.Label>
                            <Form.Control type="text" placeholder="Kosten eintragen" onChange={() => setSelectedEntry()} value={selectedEntry.costs} />
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group className="CategoryArea">
                            <Form.Label>Kategorien</Form.Label>
                            <div className="Multiselect">
                                <Select options={mappedCategories} components={animatedComponents} onChange={(e) => setSelectedCategories(e)}
                                        isMulti />
                            </div>
                        </Form.Group>
                    </Col>
                    <Col className="buttonCol">
                        <Form.Group className="buttonArea">
                            <Button onClick={() => AddEntry(selectedEntry)}>Eintrag erstellen</Button>
                            {!showMore &&  <Button variant="link" onClick={() => setShowMore(true)}>Zeig mehr</Button>}
                        </Form.Group>
                    </Col>
                </Row>
                <br/>
                { showMore &&
                <Row>
                    <Col>
                        <Form.Group>
                            <Form.Label>Datum</Form.Label>
                            <Form.Control type="text" placeholder="Datum eintragen" onChange={() => setSelectedEntry()} value={selectedEntry.timestamp}/>
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group>
                            <Form.Label>Beschreibung</Form.Label>
                            <Form.Control as="textarea" rows={3} onChange={() => setSelectedEntry()} />
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group>
                            <Button variant="link" onClick={() => setShowMore(false)}>Zeig weniger</Button>
                        </Form.Group>
                    </Col>
                </Row>}
            </Form>
                    </Card.Body>
                </Card>
            </CardGroup>

            <CardGroup>
                <Diagram1 selectedGroup={selectedGroup}/>
                <Diagram2 selectedGroup={selectedGroup}/>
                <Diagram3 selectedGroup={selectedGroup}/>
            </CardGroup>

            <CardGroup>
                <EntryTable entries = {entrys} selectedEntry={selectedEntry} setSelectedEntry={setSelectedEntry} DeleteEntry={DeleteEntry} />
            </CardGroup>

        </>
    )
}
export default Homepage