/* eslint-disable react-hooks/exhaustive-deps */
import React, {useEffect, useReducer, useState} from "react";
import {ArcElement, BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, LineElement, PointElement, Title, Tooltip} from 'chart.js';
import "../../css/styles.scss"
import "../../css/homepage.scss"
import {CardGroup, Row} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import {useHistory} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {fetchCategoriesFromServer, selectCategoryStore} from "../../reduxStore/CategorySlice";
import {
    addSavingEntryToServer,
    deleteSavingEntryFromServer,
    fetchSavingEntriesFromServer,
    selectSavingEntryStore
} from "../../reduxStore/SavingEntrySlice";
import {fetchGeneralInformationToGroupFromServer, fetchGroupCoreInformationFromServer, selectGroupInformationStore} from "../../reduxStore/GroupInformationSlice";
import {login, logout, selectUserStore} from "../../reduxStore/UserSlice";
import KeyCloakService from "../../api/Auth";
import {
    fetchProcessingResultsFromServer,
    removeSortedAndFilteredSavingEntry,
    selectProcessingStore
} from "../../reduxStore/ProcessingSlice";
import {Diagram1} from "./Diagrams/Diagram1";
import {Diagram2} from "./Diagrams/Diagram2";
import {Diagram3} from "./Diagrams/Diagram3";
import {EntryTable} from "./EntryTable";
import {NavigationBar} from "./NavigationBar";
import {EntryCreationBar} from "./EntryCreationBar";
import {SearchBar} from "./SearchBar";

ChartJS.register(ArcElement, Tooltip, Legend, CategoryScale, LinearScale, BarElement, Title, PointElement, LineElement);


const Homepage = ({groups, AddGroup, DeleteGroup, AddEntry, getActiveGroupId,setActiveGroupId}) => {

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
        dispatch(login(KeyCloakService.getToken()));

        dispatch(fetchGroupCoreInformationFromServer())
            .then(setLoadingCoreInformation(true));

        // eslint-disable-next-line react-hooks/exhaustive-deps
    },[])

    useEffect(()=>{
        if(categoryStore != null && Array.isArray(categoryStore))
            setSelectedFilterCategories(categoryStore)
    },[categoryStore])

    const [isLoadingCoreInformation, setLoadingCoreInformation] = useState(false)

    useEffect( () => {
        if(!isLoadingCoreInformation) return;

        fetchContentInformation();
        setLoadingCoreInformation(false);
        setActiveGroupId(groupInformationStore.find(group => group.personGroup === true).id);
    },[groupInformationStore])


    console.log("In Render GroupInformationStore:")
    console.log(groupInformationStore)

    console.log("In Render savingEntryStore:")
    console.log(savingEntryStore)

    console.log("In Render categoryStore:")
    console.log(categoryStore)

    console.log("In Render processingStore:")
    console.log(processingStore)

    const fetchContentInformation = () => {

        //Get personal group
        let personGroup = groupInformationStore.find(group => group.personGroup === true);

        //Fetch all general information about the groups
        for (let group of groupInformationStore){
            dispatch(fetchGeneralInformationToGroupFromServer(group.id))
        }

        //fetch categories for this group
        dispatch(fetchCategoriesFromServer(personGroup.id));
        //fetch saving entries for this group
        dispatch(fetchSavingEntriesFromServer(personGroup.id));
        //fetch processing results
        dispatch(fetchProcessingResultsFromServer(personGroup.id, defaultFilterInformation))

    }

    KeyCloakService.updateToken()
        .then((refreshed) => refreshToken(refreshed))
        .catch(function() {
            dispatch((logout()))
            console.log('Failed to refresh the token, or the session has expired');
        });

    const refreshToken = (refreshed) => {
        if (refreshed) {
            dispatch(login(KeyCloakService.getToken()));
        } else {
            console.log('Token is still valid');
        }
    };

    const entryAction = {
        addCreator: "addCreator",
        updateEntryName : "updateEntryName",
        updateEntryDesc : "updateEntryDesc",
        updateEntryCostBalance : "updateEntryCostBalance",
        updateEntryCategory : "updateEntryCategory",
        updateEntryCreationDate : "updateEntryCreationDate",
        updateEntry : "updateEntry"
    }
    const entryReducer = (state, action) => {
        console.log("ACTION: ",action)
        console.log("STATE: ",state)
        switch (action.type){
            case entryAction.updateEntryName:
                return {...state, name: action.payload}
            case entryAction.updateEntryDesc:
                return {...state, description: action.payload}
            case entryAction.updateEntryCostBalance:
                return {...state, costBalance: action.payload}
            case entryAction.updateEntryCategory:
                return {...state, category: action.payload}
            case entryAction.updateEntryCreationDate:
                return {...state, creationDate: action.payload}
            case entryAction.updateEntry:
                return {
                    ...state,
                    name: action.payload.name,
                    description: action.payload.description,
                    costBalance: action.payload.costBalance,
                    creationDate: action.payload.creationDate,
                    category: action.payload.category
                }
            case entryAction.addCreator:
                return {...state, creator: action.payload}
            default:
                return state;
        }
    }
    let initialEntryState = {
        name: null,
        description: null,
        costBalance: null,
        creationDate: null,
        category: null,
        creator: null
    }

    const [entryState, dispatchEntry] = useReducer(entryReducer, initialEntryState);



    /**
     * Local states
     */
    const [selectedGroup = groups[0], setSelectedGroup] = useState()
    const [selectedSettingsGroup = groups[0], setSelectedSettingsGroup] = useState()

    //CreateEntryBar states for entry creation parameters
    const [selectedEntry, setSelectedEntry] = useState()
    const [showMore, setShowMore] = useState(false)

    //SearchBar states for search parameters
    const [selectedUsers, setSelectedUsers] = useState()
    const [selectedFilterCategories, setSelectedFilterCategories] = useState([])
    const [selectedTimeWindow, setSelectedTimeWindow] = useState()
    const timeWindow = [{label : "day"},{label : "week"},{label : "month"},{label : "year"}]


    const history = useHistory()

    const navToGuestSite = () => {
        history.push("/");
    }

    const deleteEntry = (id) => {
        console.log(id)
        dispatch(deleteSavingEntryFromServer(getActiveGroupId, id));
        dispatch(removeSortedAndFilteredSavingEntry(id))
        setSelectedEntry(null);
    }
    const addEntry = (id) => {
        console.log(id)
        dispatch(addSavingEntryToServer(getActiveGroupId, id))
            .then(() => {
                dispatch(fetchProcessingResultsFromServer(getActiveGroupId, defaultFilterInformation))
                setSelectedEntry(null);
            })
    }

    return (
        <React.Fragment>

            {/**
             Navigation bar to log in / logout, chat and change active group
             */}
             <NavigationBar groups={groups}
                            setSelectedGroup={setSelectedGroup}
                            selectedGroup={selectedGroup}
                            selectedSettingsGroup={selectedSettingsGroup}
                            AddGroup={AddGroup}
                            DeleteGroup={DeleteGroup}
                            navToGuestSite={navToGuestSite}
                            />

            {/**
             Searchbar, which is a bar to create entries?
             */}
            <EntryCreationBar setSelectedEntry = {setSelectedEntry}
                              selectedEntry = {selectedEntry}
                              mappedCategories = {mappedCategories}
                              AddEntry = {addEntry}
                              entryAction = {entryAction}
                              entry = {entryState}
                              setEntry = {dispatchEntry}
                              setShowMore = {setShowMore}
                              showMore = {showMore}
            />
            <SearchBar mappedCategories = {mappedCategories}
                       setSelectedFilterCategories = {setSelectedFilterCategories}
                       selectedFilterCategories = {selectedFilterCategories}
                       users = {[{label:"Testuser1"},{label:"Testuser2"}]}
                       selectedUsers = {selectedUsers}
                       setSelectedUsers = {setSelectedUsers}
                       timeWindow = {timeWindow}
                       selectedTimeWindow = {selectedTimeWindow}
                       setSelectedTimeWindow = {setSelectedTimeWindow}
            />


            {/**
             Diagrams displaying the results from processing controller
             */}
            <CardGroup>
                <Diagram1 diagramValues={processingStore.balanceProcessResultDTO}/>
                <Diagram2 selectedGroup={selectedGroup} selectedCategories={selectedFilterCategories}/>
                <Diagram3 selectedGroup={selectedGroup}/>
            </CardGroup>

            {/**
                Table showing all entries
             */}
            <CardGroup>
                <EntryTable entries={processingStore.savingEntryDTOs}
                            selectedEntry={selectedEntry}
                            setSelectedEntry={dispatchEntry}
                            deleteEntry={deleteEntry}
                            entryAction={entryAction}
                />
            </CardGroup>

        </React.Fragment>
    )
}

export default Homepage