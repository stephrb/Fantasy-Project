import React, { useEffect, useState } from 'react';
import axios from 'axios'
import ModelService from '../../services/ModelService';
import Matchup from './Matchup';

function MatchupList(props) {
    const [matchups, setMatchups] = useState();
    const [reset, setReset] = useState(true)
    useEffect(() => {
        const controller = new AbortController();
        ModelService.getMatchupsWinPercentages(props.matchupPeriod, props.assessInjuries, props.numGames, { signal: controller.signal })
          .then((res) => {
            setMatchups(res.data);
          })
          .catch((error) => {
            if (axios.isCancel(error)) {
              console.log("Request canceled", error.message);
            } else {
              console.log(error);
            }
          });
        return () => {
          controller.abort();
        };
      }, [props.matchupPeriod, props.assessInjuries, props.numGames, reset]);

      const handleClose = () => {
        setReset(!reset)
      }
    return (
      <div>
        {matchups &&
          matchups.map((matchup) => (
            <Matchup 
              matchupPeriod={props.matchupPeriod} 
              assessInjuries={props.assessInjuries} 
              numGames={props.numGames}
              handleClose={handleClose}
              key={matchup.matchupId}{...matchup} />
        ))}
    </div>
    );
}

export default MatchupList;