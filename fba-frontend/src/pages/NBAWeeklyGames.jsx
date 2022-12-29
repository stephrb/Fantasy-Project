import React, { useState, useEffect } from "react";
import Header from "../components/ui/Header";
import WeekSelector from "../components/nbagames/WeekSelector";
import NBATable from "../components/nbagames/NBATable";
import ModelService from "../services/ModelService";
function NBAWeeklyGames(props) {
  const [matchupPeriod, setMatchupPeriod] = useState();

  useEffect(() => {
    ModelService.getCurrentMatchupPeriod().then((res) =>
      setMatchupPeriod(res.data)
    );
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
      <WeekSelector changeMatchupWeekHandler={changeMatchupWeekHandler} />
      <NBATable matchupPeriod={matchupPeriod} />
    </div>
  );
}

export default NBAWeeklyGames;