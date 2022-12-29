import React, { useEffect, useState } from "react";
import Header from "../components/ui/Header";
import ModelService from "../services/ModelService";
import MatchupPeriodButtons from "../components/projections/MatchupPeriodButtons";
import ScoreProjectionsBody from "../components/projections/ProjectionsBody";
import axios from 'axios'

function ScoreProjections(props) {
  const [assessInjuries, setAssessInjuries] = useState(true);
  const [timePeriod, setTimePeriod] = useState("Season_2023");
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

  function setTimePeriodHandler(tp) {
    setTimePeriod(tp);
  }

  function toggleAssessInjuriesHandler() {
    setAssessInjuries(!assessInjuries);
  }

  function changeMatchupWeekHandler(week) {
    setMatchupPeriod(week);
  }

  if (typeof matchupPeriod === "undefined") {
    return <p>loading...</p>;
  }
  return (
    <div>
      <Header text="Score Projections" />
      <MatchupPeriodButtons
        changeMatchupWeekHandler={changeMatchupWeekHandler}
        matchupPeriod={matchupPeriod}
      />
      <ScoreProjectionsBody
        setTimePeriodHandler={setTimePeriodHandler}
        toggleAssessInjuriesHandler={toggleAssessInjuriesHandler}
        assessInjuries={assessInjuries}
        timePeriod={timePeriod}
        matchupPeriod={matchupPeriod}
      />
    </div>
  );
}

export default ScoreProjections;
