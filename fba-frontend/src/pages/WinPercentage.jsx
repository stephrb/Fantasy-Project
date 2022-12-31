import React, { useEffect, useState } from 'react';
import Header from '../components/ui/Header';
import ModelService from '../services/ModelService';
import axios from 'axios';

// import MatchupWeek from '../components/playoffmachine/MatchupWeek';
import MatchupPeriods from '../components/winpercentage/MatchupPeriods';
import WinPercentageBody from '../components/winpercentage/WinPercentageBody';
function WinPercentage(props) {
  const [matchupPeriod, setMatchupPeriod] = useState();

  useEffect(() => {
    const controller = new AbortController();
    
    ModelService.getCurrentMatchupPeriod({ signal: controller.signal }).then((res) =>
      setMatchupPeriod(res.data)
    ).catch((error) => {
      if (axios.isCancel(error)) {
        console.log("Request canceled", error.message);
      } else {
        console.log(error);
      }
    });

  return () => {
    controller.abort();
  };
  }, []);

  function changeMatchupWeekHandler(matchupPeriod) {
    setMatchupPeriod(matchupPeriod);
  }
  if (typeof matchupPeriod === "undefined") {
    return <p>loading...</p>;
  }

  return (
    <div>
      <Header text="Matchup Win Probabilities"></Header>
      <MatchupPeriods changeMatchupWeekHandler={changeMatchupWeekHandler}/>
      <WinPercentageBody matchupPeriod={matchupPeriod}/>
    </div>
  );
}

export default WinPercentage;