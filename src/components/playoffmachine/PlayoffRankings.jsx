import React from 'react';
import Card from '../ui/Card';
import classes from './PlayoffRankings.module.css';
function PlayoffRankings(props) {
    const playoffRankings = props.playoffRankings;
    return (
            <Card>
                <table className={classes.styledtable}>
                    <thead>
                        <tr>
                            <th scope="col">Playoff Seed</th>
                            <th scope="col">Team Name</th>
                            <th scope="col">Record</th>
                        </tr>
                    </thead>
                    <tbody >
                        {
                            playoffRankings.map(
                                (team, index) => 
                                <tr key = {index + 2000}>
                                    <td> {index + 1}</td>
                                    <td> {team.name}</td>
                                    <td> {team.record}</td>
                                </tr>
                            )
                        }
                    </tbody>
                </table>
            </Card>
       
    );
}

export default PlayoffRankings;