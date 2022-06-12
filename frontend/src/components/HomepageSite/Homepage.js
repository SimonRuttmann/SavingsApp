/* eslint-disable react-hooks/exhaustive-deps */
// noinspection JSCheckFunctionSignatures

import React, {useEffect, useReducer, useState} from "react";
import {ArcElement, BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, LineElement, PointElement, Title, Tooltip} from 'chart.js';
import "../../css/styles.scss"
import "../../css/homepage.scss"
import {Card, CardGroup} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import {useHistory} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {addCategoryToServer, deleteCategoryFromServer, fetchCategoriesFromServer, selectCategoryStore, updateCategoryToServer} from "../../reduxStore/CategorySlice";
import {fetchGeneralInformationToGroupFromServer, fetchGroupCoreInformationFromServer, selectGroupInformationStore} from "../../reduxStore/GroupInformationSlice";
import {fetchUserDataFromServer, login, logout, selectUserStore} from "../../reduxStore/UserSlice";
import KeyCloakService from "../../api/Auth";
import {addSavingEntryToServer, deleteSavingEntryFromServer, fetchProcessingResultsFromServer, selectProcessingStore, updateSavingEntryToServer} from "../../reduxStore/ContentSlice";
import {Diagram1} from "./Diagrams/Diagram1";
import {Diagram2} from "./Diagrams/Diagram2";
import {Diagram3} from "./Diagrams/Diagram3";
import {EntryTable} from "./EntryTable";
import {NavigationBar} from "./NavigationBar";
import {EntryCreationBar} from "./EntryCreationBar";
import {SearchBar} from "./SearchBar";
import UpdateSavingEntry from "./UpdateSavingEntry";
import CategoryEditingBar from "./CategoryEditingBar";

ChartJS.register(ArcElement, Tooltip, Legend, CategoryScale, LinearScale, BarElement, Title, PointElement, LineElement);


export const filterInformationAction = {
    changeFilterTimeInterval: "changeFilterTimeInterval",
    changeFilterUsers : "changeFilterUsers",
    changeFilterCategories : "changeFilterCategories",
    changeStartDate: "changeStartDate",
    changeEndDate: "changeEndDate"
}


const Homepage = ({groups, AddGroup, DeleteGroup, getActiveGroupId,setActiveGroupId}) => {


    /**
     *  Access Redux-Stores
     */

    const groupInformationStore = useSelector(selectGroupInformationStore);
    const userStore             = useSelector(selectUserStore);
    const categoryStore         = useSelector(selectCategoryStore);
    const processingStore       = useSelector(selectProcessingStore);
    const dispatch = useDispatch()


    /**
     * Configure Keycloak
     * -----------------------------------------------------------------------------------------------------------------
     */

    KeyCloakService.updateToken()
        .then((refreshed) => refreshToken(refreshed))
        .catch(function() {
            dispatch((logout()))
        });


    const refreshToken = (refreshed) => {
        if (refreshed) {
            dispatch(login(KeyCloakService.getToken()));
        }
    };

    const history = useHistory()

    const navToGuestSite = () => {
        history.push("/");
    }

    /**
     * Initialize stores with data fetched from servers
     * -----------------------------------------------------------------------------------------------------------------
     */
    const currentFilterInformationReducer = (state, action) => {
        switch (action.type){
            case filterInformationAction.changeFilterTimeInterval:
                return {...state, timeInterval: action.payload.label}
            case filterInformationAction.changeFilterUsers:
                return {...state, filterUser: action.payload}
            case filterInformationAction.changeFilterCategories:
                return {...state, filterCategory: action.payload}
            case filterInformationAction.changeStartDate:
                console.log("CHANGE START DATE")
                console.log(action.payload)
                return {...state, startDate: action.payload}
            case filterInformationAction.changeEndDate:
                return {...state, endDate: action.payload}
            default:
                return state;
        }
    }

    const [currentFilterInformation, dispatchFilterInformation] = useReducer(currentFilterInformationReducer, {
        "sortParameter": "CreationDate",
        "timeInterval": "Day",
        "startDate": null,
        "endDate": null,
        "filterUser": [],
        "filterCategory": []
    });

    const currentFilterInformationToDataObject = () => {
        return {
            "sortParameter": currentFilterInformation.sortParameter,
            "timeInterval": currentFilterInformation.timeInterval,
            "startDate": currentFilterInformation.startDate,
            "endDate": currentFilterInformation.endDate,
            "personIds": currentFilterInformation.filterUser.map(user => user.id),
            "categoryIds": currentFilterInformation.filterCategory.map(category => category.id)
        }
    }


    const [isLoadingCoreInformation, setLoadingCoreInformation] = useState(false)

    // Fetch core data
    useEffect( () => {
        dispatch(login(KeyCloakService.getToken()));
        dispatch(fetchUserDataFromServer());
        dispatch(fetchGroupCoreInformationFromServer())
            .then(setLoadingCoreInformation(true));

        // eslint-disable-next-line react-hooks/exhaustive-deps
    },[])

    // Fetch additional content data
    useEffect( () => {
        if(!isLoadingCoreInformation) return;
        fetchContentInformation();
        setLoadingCoreInformation(false);
        setActiveGroupId(groupInformationStore.find(group => group.personGroup === true).id);
    },[groupInformationStore])


    const fetchContentInformation = () => {

        //Get personal group
        let personGroup = groupInformationStore.find(group => group.personGroup === true);

        //Fetch all general information about the groups
        for (let group of groupInformationStore){
            dispatch(fetchGeneralInformationToGroupFromServer(group.id))
        }

        //fetch categories for this group
        dispatch(fetchCategoriesFromServer(personGroup.id));
        //fetch processing results
        let dataObject = currentFilterInformationToDataObject();
        dispatch(fetchProcessingResultsFromServer(personGroup.id, dataObject))

    }


    /**
     * Update local states
     * -----------------------------------------------------------------------------------------------------------------
     */

    console.log("In Render GroupInformationStore:")
   console.log(groupInformationStore)

    console.log("In Render categoryStore:")
    console.log(categoryStore)

    console.log("In Render processingStore:")
    console.log(processingStore)

    const getUsers = () =>{
        try {
            const info = groupInformationStore.find(group => group.id === getActiveGroupId).personDTOList;
            if(info == null) return [];
            return info;
        }catch (e){return []}
    }


    const mappedCategories = categoryStore.map(category =>{
        return{label: category.name, value: category.id, ...category}
    });


    const [selectedGroup = groups[0], setSelectedGroup] = useState()
    const [selectedSettingsGroup = groups[0], setSelectedSettingsGroup] = useState()


    /**
     * Crud saving entry
     * -----------------------------------------------------------------------------------------------------------------
     */

    const [selectedEntry, setSelectedEntry] = useState()
    const [showMore, setShowMore] = useState(false)

    const addEntry = (entry) => {
        entry.creator = userStore.username;
        dispatch(addSavingEntryToServer(getActiveGroupId, entry))
            .then(() => {
                dispatch(fetchProcessingResultsFromServer(getActiveGroupId, currentFilterInformationToDataObject()))
            })
        setSelectedEntry(null);
    }

    const deleteEntry = (id) => {
        dispatch(deleteSavingEntryFromServer(getActiveGroupId, id))
            .then(() => {
                dispatch(fetchProcessingResultsFromServer(getActiveGroupId, currentFilterInformationToDataObject()))
            })
        setSelectedEntry(null);
    }

    const updateEntry = (entry) => {
        console.log("UPDATE ENTRY EFFECTIVE")
        console.log(entry)
        dispatch(updateSavingEntryToServer(getActiveGroupId, entry))
            .then( () => {
                dispatch(fetchProcessingResultsFromServer(getActiveGroupId, currentFilterInformationToDataObject()))
            })
        setSelectedEntry(null);
    }



    /**
     * Crud category
     * -----------------------------------------------------------------------------------------------------------------
     */

    const [selectedCategory, setSelectedCategory] = useState()

    const addCategory = (category) => {
        dispatch(addCategoryToServer(getActiveGroupId, category))
            .then( () => {
                dispatch(fetchProcessingResultsFromServer(getActiveGroupId, currentFilterInformationToDataObject()))
            })
        setSelectedCategory(null);
    }

    const deleteCategory = (id) => {
        dispatch(deleteCategoryFromServer(getActiveGroupId, id))
            .then( () => {
                dispatch(fetchProcessingResultsFromServer(getActiveGroupId, currentFilterInformationToDataObject()))
            })
        setSelectedCategory(null);
    }

    const updateCategory = (category) => {
        dispatch(updateCategoryToServer(getActiveGroupId, category))
            .then( () => {
                dispatch(fetchProcessingResultsFromServer(getActiveGroupId, currentFilterInformationToDataObject()))
            })
        setSelectedCategory(null);
    }

    console.log("CURRENT FILTER INFORMATION")
    console.log(currentFilterInformation)

    const [openUpdateEntryPopup, setOpenUpdateEntryPopup] = useState(false);

    const triggerUpdateEntry = () => {
        setOpenUpdateEntryPopup(prevState => !prevState);
    }


    return (
        <React.Fragment>


             <NavigationBar getActiveGroupId={getActiveGroupId}
                            groups={groups}
                            realGroups={groupInformationStore}
                            setSelectedGroup={setSelectedGroup}
                            selectedGroup={selectedGroup}
                            selectedSettingsGroup={selectedSettingsGroup}
                            AddGroup={AddGroup}
                            DeleteGroup={DeleteGroup}
                            navToGuestSite={navToGuestSite}
                            />

            {openUpdateEntryPopup ?
                <UpdateSavingEntry
                    setOpenUpdateEntryPopup={setOpenUpdateEntryPopup}
                    selectedEntry = {selectedEntry}
                    updateEntry={updateEntry}
                    mappedCategories={mappedCategories} /> : null}


            <Card className="subHeader">
                <h4>Suchleiste</h4>
            </Card>

            <SearchBar mappedCategories = {mappedCategories}
                       users = {getUsers()}
                       currentFilterInformation = {currentFilterInformation}
                       dispatchFilterInformation = {dispatchFilterInformation}
            />


            {/**
             Diagrams displaying the results from processing controller
             */}
            <CardGroup>
                <Diagram1 diagramValues={processingStore.balanceProcessResultDTO} />

                <Diagram2 diagramValues={processingStore.diagramByIntervalAndCategory}
                          selectedCategories={currentFilterInformation.filterCategory.length === 0 ? categoryStore : currentFilterInformation.filterCategory}
                          defaultFilterInformation={currentFilterInformation}/>

                <Diagram3 diagramValues={processingStore.diagramByIntervalAndCreator}
                          selectedUsers={currentFilterInformation.filterUser.length === 0 ? getUsers(): currentFilterInformation.filterUser}
                          defaultFilterInformation={currentFilterInformation}/>
            </CardGroup>

            <Card className="subHeader">
                <h4>Eintragserstellung</h4>
            </Card>

            <EntryCreationBar setSelectedEntry = {setSelectedEntry}
                              selectedEntry = {selectedEntry}
                              mappedCategories = {mappedCategories}
                              AddEntry = {addEntry}
                              setShowMore = {setShowMore}
                              showMore = {showMore}
            />


            <Card className="subHeader">
                <h4>Kategoriebearbeitung</h4>
            </Card>

            <CategoryEditingBar addCategory={addCategory} deleteCategory={deleteCategory} updateCategory={updateCategory} mappedCategories={mappedCategories}/>


            {/**
                Table showing all entries
             */}
            <CardGroup>
                <EntryTable entries={processingStore.savingEntryDTOs}
                            selectedEntry={selectedEntry}
                            setSelectedEntry={setSelectedEntry}
                            deleteEntry={deleteEntry}
                            openUpdateEntry={triggerUpdateEntry}
                />
            </CardGroup>

        </React.Fragment>
    )
}

export default Homepage