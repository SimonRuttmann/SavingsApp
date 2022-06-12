import {Button, ButtonGroup, Card, Table} from "react-bootstrap";
import React from "react";


export const EntryTable = ({entries, selectedEntry, setSelectedEntry, deleteEntry, openUpdateEntry}) => {

    return (
        <Card>
            <Card.Body>
                <h4>Einträge</h4>
                <br/>
                <ButtonGroup className="buttonStyle">
                    <Button
                        onClick={() => {
                            if(selectedEntry == null) return;
                            openUpdateEntry(selectedEntry)
                        }}
                        variant="secondary">Update</Button>
                    <Button onClick={() => deleteEntry(selectedEntry.id)} variant="secondary">Löschen</Button>
                </ButtonGroup>
                <Table className="entryTable" striped bordered hover>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Kosten</th>
                        <th>User</th>
                        <th>Kategorie</th>
                        <th>Zeitpunkt</th>
                    </tr>
                    </thead>
                    <tbody>
                    {entries == null ? null : entries.map(entry =>
                        <tr className={selectedEntry!= null && entry.id === selectedEntry.id ? "selectedEntry" : ""} key={`Entry-${entry.id}`} onClick={() => setSelectedEntry(entry)}>
                            <td>{entry.name}</td>
                            <td>{entry.costBalance +"€"}</td>
                            <td>{entry.creator}</td>
                            <td>{entry.category.name}</td>
                            <td>{entry.creationDate}</td>
                        </tr>
                    )}
                    </tbody>
                </Table>
            </Card.Body>
        </Card>
    )
}