import React, { useState, useEffect } from "react";
import Header from "../components/ui/Header";
import WeekSelector from "../components/nbagames/WeekSelector";
import NBATable from "../components/nbagames/NBATable";
import ModelService from "../services/ModelService";
import axios from 'axios'

function NBAWeeklyGames(props) {
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
      <Header text="NBA Weekly Games" />
      <WeekSelector matchupPeriod={matchupPeriod} changeMatchupWeekHandler={changeMatchupWeekHandler} />
      <NBATable matchupPeriod={matchupPeriod} />
    </div>
  );
}

export default NBAWeeklyGames;