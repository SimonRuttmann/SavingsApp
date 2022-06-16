import {Button, ButtonGroup, Card, Table} from "react-bootstrap";
import React from "react";


export const EntryTable = ({entries, selectedEntry, setSelectedEntry, deleteEntry, openUpdateEntry}) => {

    return (
        <Card>
            <h4>Einträge</h4>
            <ButtonGroup className="buttonStyle">
                <Button
                    onClick={() => {
                        if(selectedEntry == null) return;
                        openUpdateEntry(selectedEntry)
                    }}
                    variant="secondary">Eintrag ändern</Button>
                <Button onClick={() => deleteEntry(selectedEntry.id)} variant="secondary">Eintrag löschen</Button>
            </ButtonGroup>
            <Card.Body>
                <Table responsive={"sm"} size={"sm"} className="entryTable" striped bordered hover>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Kosten</th>
                        <th>User</th>
                        <th>Kategorie</th>
                        <th>Zeitpunkt</th>
                        <th>Beschreibung</th>
                    </tr>
                    </thead>
                    <tbody>
                    {entries == null ? null : entries.map(entry =>
                        <tr className={selectedEntry!= null && entry.id === selectedEntry.id ? "selectedEntry" : ""} key={`Entry-${entry.id}`} onClick={() => setSelectedEntry(entry)}>
                            <td>{entry.name}</td>
                            <td>{entry.costBalance +"€"}</td>
                            <td>{entry.creator}</td>
                            <td>{entry.category.name}</td>
                            <td>{new Date(entry.creationDate).toLocaleDateString("de-DE")}</td>
                            <td>{entry.description}</td>
                        </tr>
                    )}
                    </tbody>
                </Table>
            </Card.Body>
        </Card>
    )
}