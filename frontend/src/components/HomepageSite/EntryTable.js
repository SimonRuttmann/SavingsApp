import {Button, ButtonGroup, Card, Table} from "react-bootstrap";
import React from "react";

export const EntryTable = ({entries, selectedEntry, setSelectedEntry, deleteEntry}) => {

    return (
        <Card>
            <Card.Body>
                <h4>Einträge</h4>
                <br/>
                <ButtonGroup className="buttonStyle">
                    <Button variant="secondary">Alle</Button>
                    <Button variant="secondary">WG</Button>
                    <Button variant="secondary">FAM</Button>
                    <Button variant="secondary">Ich</Button>
                </ButtonGroup>
                <ButtonGroup className="buttonStyle">
                    <Button onClick={() => deleteEntry(selectedEntry.id)} variant="secondary">Eintrag löschen</Button>
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
                        <tr className={entry.id === selectedEntry.id ? "selectedEntry" : ""} key={`Entry-${entry.id}`} onClick={() => setSelectedEntry(entry)}>
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