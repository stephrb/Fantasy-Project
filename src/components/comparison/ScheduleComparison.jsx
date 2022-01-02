import React, { useState, useEffect } from 'react';
import ModelService from '../../services/ModelService';
import Card from '../ui/Card';
function ScheduleComparison(props) {
    const [teamRecords, setTeamRecords] = useState();

    useEffect(() => {
        ModelService.getScheduleComparison().then((res) => {
            setTeamRecords(res.data);
        });
    }, []);


    if (typeof teamRecords === 'undefined') {
        return (
            <p>
                loading...
            </p>
        )
    }
    return (
        <Card>
            <table>
                <thead>
                    <tr>
                        <th>Team Name</th>
                    {
                    teamRecords.map(
                        teamRecord =>
                            <th key={(teamRecord.teamName)[0]} scope="col">{(teamRecord.teamName)[0]}</th>
                    )
                }
                    </tr>
               
                </thead>
                <tbody>
                   {
                       teamRecords.map(
                           teamRecord =>
                           <tr key= {(teamRecord.teamName)[0]}>
                               <td>
                                   <b>{(teamRecord.teamName)[0]}</b>
                                </td>
                               {
                                   teamRecord.records.map(
                                       (record, index) =>
                                       <td key={(teamRecord.teamName)[0]}>{record}</td>
                                   )
                               }
                           </tr>
                       )
                   }
                </tbody>
            </table>
        </Card>
    );
}

export default ScheduleComparison;