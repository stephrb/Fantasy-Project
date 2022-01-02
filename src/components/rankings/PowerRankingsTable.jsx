import React, { useState, useEffect } from 'react';
import ModelService from '../../services/ModelService';
import Card from '../ui/Card';
import classes from './PowerRankingsTable.module.css';

function PowerRankingsTable(props) {
    const [teams, setTeams] = useState([]);
    

   
    useEffect(() => {
       ModelService.getPowerRankings().then((res) => {
           setTeams(res.data);
       });
    }, []);


    console.log(teams);
    return (
        <Card>
            <table className={classes.styledtable}>
                    <thead>
                        <tr>
                            <th scope="col">Team Name</th>
                            <th scope="col">Record</th>
                            <th scope="col">Power Score</th>
                        </tr>
                    </thead>
                    <tbody >
                        {
                            teams.map(
                                team => 
                                <tr key = {team.powerRankingScore}>
                                    <td> {team.name}</td>
                                    <td> {team.record}</td>
                                    <td> {team.powerRankingScore}</td>
                                </tr>
                            )
                        }
                    </tbody>
                </table>
                <p className={classes.footer}>Calulated by (Points Scored + (Points Scored * Winning %) 
                    + (Points Scored vs the median score of the week)</p>
            </Card>
    );
}

export default PowerRankingsTable;