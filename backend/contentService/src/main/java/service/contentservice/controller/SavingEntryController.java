package service.contentservice.controller;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import service.contentservice.persistence.documentbased.SavingEntry;



@RestController
@RequestMapping("/group")
public class SavingEntryController {


// Returns all information for a given group
    @GetMapping("/{groupId}")
    public String getGeneralGroupInformation(@PathVariable Integer groupId){
        return "";
    }

//Returns filtered information for a given group
    @GetMapping("/{groupId}/entry")
    public String getFilteredSavingEntries(
            @PathVariable(value="groupId") Integer groupId,
            @RequestParam MultiValueMap<String,String> queryParameters){

        return "";
    }
    // Filter:
    // Anfangsdatum
    // Zeitintervall
    //

    //Diagramm 1
    // Summe abhängig von Filtern
    // 1. Zeitraum --> Anfangsdatum ist gegeben
    // 2. User: Array an Usern, nur user beachten, welche darin liegen
    // 3. Kategory: Array an KategorieId`s, nur beachten welche darin liegen

    //Wert 1 Typ: Integer Summe der Einnahmen
    //Wert 2 Typ: Integer Summe der Ausgaben


    //Diagramm 2
    // 1. Zeitraum --> Anfangsdatum ist gegeben, alle anderen weg
    // 2. Zeitintervall --> Zeit in sekunden
    // 3. User + Kategorie wie voher

    // Array für jedes Zeitintervall
    // Zeitintervall:
    //      Kategorie 1: Summe ausgaben (für alle user)
    //      Kategorie 2: Summe ausgaben
    //      Kategorie 3: Summe ausgaben


    //Diagramm 3
    // 1. Zeitraum --> Anfangsdatum ist gegeben, alle anderen weg
    // 2. Zeitintervall --> Zeit in sekunden
    // 3. User + Kategorie wie voher

    // Array für jedes Zeitintervall
    // Zeitintervall:
    //      user 1: Summe ausgaben (für alle kategorien)
    //      user 2: Summe ausgaben
    //      user 3: Summe ausgaben


    //Diagramm 4
    // 1. Zeitraum --> Anfangsdatum ist gegeben, alle anderen weg
    // 2. Zeitintervall --> Zeit in sekunden

    // Array für jedes Zeitintervall
    // Zeitintervall:
    //      Summe ausgaben
    //      Summe ausgaben
    //      Summe ausgaben

    //Anzahl = Zeitintervall bisher / 2
    // Calculated ..
    // mittelwert * inflation



    //Default get, put, post, delete entry

    //get entry
    @GetMapping("/{groupId}/entry/{entryId}")
    public String getSavingEntry(
            @PathVariable(value="groupId") Integer groupId){

        return "";
    }


    //update entry
    @PutMapping(
            value="/{groupId}/entry",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String updateSavingEntry(
            @PathVariable(value="groupId") Integer groupId,
            @RequestBody SavingEntry savingEntry){

        return "";
    }


    //Creates entry (saving entry)
    @PostMapping(
            value="/{groupId}/entry",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String addSavingEntry(
            @PathVariable(value="groupId") Integer groupId,
            @RequestBody SavingEntry savingEntry){

        return "";
    }


    //deletes entry (saving entry)
    @DeleteMapping("/{groupId}/entry/{entryId}")
    public String deleteSavingEntry(
            @PathVariable(value="groupId") Integer groupId,
            @PathVariable(value="entryId") Integer entryId){

        return "";
    }




}

