import React, { useEffect, useState } from 'react';
import ModelService from '../../services/ModelService';
import Matchup from './Matchup';
import Card from '../ui/Card';
import classes from './MatchupWeek.module.css';
function MatchupWeek(props) {
    const [matchups, setMatchups] = useState([]);
    const week = props.week;

    useEffect(() => {
        ModelService.getPlayoffMachineMatchups().then((res) => {
            setMatchups(res.data[week]);
            
        });
     }, [week]);

     function setWinnerHomeHandler(matchup) {
         ModelService.setWinnerHome(matchup).then(props.handleSetOutcome());
     }

     function setWinnerAwayHandler(matchup) {
         ModelService.setWinnerAway(matchup).then(props.handleSetOutcome());
    }

    function setWinnerTieHandler(matchup) {
        ModelService.setWinnerTie(matchup).then(props.handleSetOutcome());
    }
     
    if (typeof matchups === 'undefined') {
        return (
            <p>
                loading...
            </p>
        )
    }
    return (
        <Card>
            <ul className={classes.container}>
                {
                    matchups.map(
                        (matchup, index) => {
                            return (
                            <div key={matchup.matchupId}>
                                <Matchup matchup={matchup} setWinnerHomeHandler={setWinnerHomeHandler} setWinnerAwayHandler={setWinnerAwayHandler} setWinnerTieHandler={setWinnerTieHandler}/>
                            </div>
                            )
                        } 
                    )
                }
            </ul>
            
        </Card>
    );
}

export default MatchupWeek;