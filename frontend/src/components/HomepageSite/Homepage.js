/* eslint-disable react-hooks/exhaustive-deps */
import React, {useEffect, useState} from "react";
import {ArcElement, BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, LineElement, PointElement, Title, Tooltip} from 'chart.js';
import "../../css/styles.scss"
import "../../css/homepage.scss"
import {CardGroup} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import {useHistory} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {fetchCategoriesFromServer, selectCategoryStore} from "../../reduxStore/CategorySlice";
import {
    deleteSavingEntryFromServer,
    fetchSavingEntriesFromServer,
    selectSavingEntryStore
} from "../../reduxStore/SavingEntrySlice";
import {fetchGeneralInformationToGroupFromServer, fetchGroupCoreInformationFromServer, selectGroupInformationStore} from "../../reduxStore/GroupInformationSlice";
import {login, logout, selectUserStore} from "../../reduxStore/UserSlice";
import KeyCloakService from "../../api/Auth";
import {fetchProcessingResultsFromServer, selectProcessingStore} from "../../reduxStore/ProcessingSlice";
import {Diagram1} from "./Diagrams/Diagram1";
import {Diagram2} from "./Diagrams/Diagram2";
import {Diagram3} from "./Diagrams/Diagram3";
import {EntryTable} from "./EntryTable";
import {NavigationBar} from "./NavigationBar";
import {SearchBar} from "./SearchBar";

ChartJS.register(ArcElement, Tooltip, Legend, CategoryScale, LinearScale, BarElement, Title, PointElement, LineElement);


const Homepage = ({groups, AddGroup, DeleteGroup, entrys, AddEntry }) => {

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

    const [isLoadingCoreInformation, setLoadingCoreInformation] = useState(false)

    useEffect( () => {
        if(!isLoadingCoreInformation) return;

        fetchContentInformation();
        setLoadingCoreInformation(false);

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
    
    
    if(debug) {
        console.log(savingEntryStore)
        console.log(groupInformationStore)
        console.log(userStore)
        console.log(categoryStore)

      //  dispatch(AddSavingEntry({id: 0, name: "Meine saving entry"}))
      //  savingEntryStore.forEach(savingEntry => console.log(savingEntry))
      //  console.log(userStore.name)
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

    const deleteEntry = (id) => {
        dispatch(deleteSavingEntryFromServer(id));
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
            <CardGroup>
                <SearchBar  setSelectedEntry={setSelectedEntry}
                            selectedEntry={selectedEntry}
                            mappedCategories={mappedCategories}
                            setSelectedCategories={setSelectedCategories}
                            AddEntry={AddEntry}
                            setShowMore={setShowMore}
                            showMore={showMore}/>
            </CardGroup>


            {/**
             Diagrams displaying the results from processing controller
             */}
            <CardGroup>
                <Diagram1 selectedGroup={selectedGroup}/>
                <Diagram2 selectedGroup={selectedGroup}/>
                <Diagram3 selectedGroup={selectedGroup}/>
            </CardGroup>

            {/**
                Table showing all entries
             */}
            <CardGroup>
                <EntryTable entries={processingStore.savingEntryDTOs} selectedEntry={selectedEntry} setSelectedEntry={setSelectedEntry} deleteEntry={deleteEntry} />
            </CardGroup>

        </React.Fragment>
    )
}

export default Homepage