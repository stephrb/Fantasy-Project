import React, { useEffect, useState } from 'react';
import axios from 'axios'
import ModelService from '../../services/ModelService';
import Matchup from './Matchup';

function MatchupList(props) {
    const [matchups, setMatchups] = useState();
    const [refresh, setRefresh] = [props.refresh, props.setRefresh]
    const [setTeamIdList, reset, setReset] = [props.setTeamIdList, props.reset, props.setReset];
    useEffect(() => {
        const controller = new AbortController();
        ModelService.getMatchupsWinPercentages(props.matchupPeriod, props.assessInjuries, props.numGames, reset, { signal: controller.signal })
          .then((res) => {
            setMatchups(res.data);
            setTeamIdList(res.data.map((a) => [a.homeTeamId, a.awayTeamId]).flat())
            setReset(false)
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
      }, [props.matchupPeriod, props.assessInjuries, props.numGames, refresh, setTeamIdList, reset, setReset]);

      const handleClose = () => {
        // setRefresh(!refresh)
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
              streamingSpots={props.streamingSpots}
              updateStreamers={props.updateStreamers}
              refresh={refresh}
              setRefresh={setRefresh}
              key={matchup.matchupId}{...matchup} />
        ))}
    </div>
    );
}

export default MatchupList;