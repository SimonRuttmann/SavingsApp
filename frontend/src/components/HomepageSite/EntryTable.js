import {Button, ButtonGroup, Card, Table} from "react-bootstrap";
import React from "react";
import {updateGroupEntry} from "../../api/services/Content";

export const EntryTable = ({entries, selectedEntry, setSelectedEntry, deleteEntry}) => {

    return (
        <Card>
            <Card.Body>
                <h4>Einträge</h4>
                <br/>
                <ButtonGroup className="buttonStyle">
                    <Button onClick={() => updateGroupEntry(selectedEntry.id)} variant="secondary">Update</Button>
                    <Button onClick={() => deleteEntry(selectedEntry.id)} variant="secondary">Löschen</Button>
                </ButtonGroup>
                <Table className="entryTable" striped bordered hover>
                    <thead>
                    <tr>
                        <th>#</th>
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
                            <td>{entry.id}</td>
                            <td>{entry.name}</td>
                            <td>{entry.costBalance}</td>
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