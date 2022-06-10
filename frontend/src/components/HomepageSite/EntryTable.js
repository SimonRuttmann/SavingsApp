import {Button, ButtonGroup, Card, Table} from "react-bootstrap";
import React from "react";

export const EntryTable = ({entries, selectedEntry, setSelectedEntry, DeleteEntry}) => {

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
                    <Button onClick={() => DeleteEntry(selectedEntry.id)} variant="secondary">Eintrag löschen</Button>
                </ButtonGroup>
                <Table striped bordered hover>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Kosten</th>
                        <th>User</th>
                        <th>Gruppe</th>
                        <th>Zeitpunkt</th>
                    </tr>
                    </thead>
                    <tbody>
                    {entries.map(entry =>
                        <tr key={`Entry-${entry.id}`} onClick={() => setSelectedEntry(entry)}>
                            <td>{entry.id}</td>
                            <td>{entry.name}</td>
                            <td>{entry.costs}</td>
                            <td>{entry.user}</td>
                            <td>{entry.group}</td>
                            <td>{entry.timestamp}</td>
                        </tr>
                    )}
                    </tbody>
                </Table>
            </Card.Body>
        </Card>
    )
}