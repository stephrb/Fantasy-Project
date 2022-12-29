import React, { useEffect, useState } from "react";
import ModelService from "../../services/ModelService";
import Matchup from "./Matchup";
import Card from "../ui/Card";
import classes from "./MatchupWeek.module.css";
import axios from 'axios'

function MatchupWeek(props) {
  const [matchups, setMatchups] = useState([]);
  const week = props.week;

  useEffect(() => {
    const controller = new AbortController();

    ModelService.getPlayoffMachineMatchups({ signal: controller.signal }).then((res) => {
      setMatchups(res.data[week]);
    }).catch((error) => {
      if (axios.isCancel(error)) {
        console.log("Request canceled", error.message);
      } else {
        console.log(error);
      }
    });

  return () => {
    controller.abort();
  };
  }, [week]);

  function setWinnerHomeHandler(matchup) {
    ModelService.setWinnerHome(matchup).then(props.handleOutcomeChange());
  }

  function setWinnerAwayHandler(matchup) {
    ModelService.setWinnerAway(matchup).then(props.handleOutcomeChange());
  }

  function setWinnerTieHandler(matchup) {
    ModelService.setWinnerTie(matchup).then(props.handleOutcomeChange());
  }

  if (typeof matchups === "undefined") {
    return <p>loading...</p>;
  }
  return (
    <Card>
      <ul className={classes.container}>
        {matchups.map((matchup, index) => {
          return (
            <div key={matchup.matchupId}>
              <Matchup
                reset={props.reset}
                matchup={matchup}
                setWinnerHomeHandler={setWinnerHomeHandler}
                setWinnerAwayHandler={setWinnerAwayHandler}
                setWinnerTieHandler={setWinnerTieHandler}
              />
            </div>
          );
        })}
      </ul>
    </Card>
  );
}

export default MatchupWeek;
