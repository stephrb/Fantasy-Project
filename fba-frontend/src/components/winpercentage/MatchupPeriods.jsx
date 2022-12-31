import React, { useEffect, useState } from "react";
import ModelService from "../../services/ModelService";
import classes from "./MatchupPeriods.module.css";
import axios from 'axios'

function MatchupPeriods(props) {
  const [allMatchups, setAllMatchups] = useState();
  const [curMatchupPeriod, setCurMatchupPeriod] = useState();

  useEffect(() => {
    const controller = new AbortController();

    ModelService.getMatchupsLeftWithPlayoffs({ signal: controller.signal }).then((res) => {
      setAllMatchups(res.data);
    }).catch((error) => {
      if (axios.isCancel(error)) {
        console.log("Request canceled", error.message);
      } else {
        console.log(error);
      }
    });

    ModelService.getCurrentMatchupPeriod({ signal: controller.signal }).then((res) => {
      setCurMatchupPeriod(res.data);
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
  }, []);

  function changeMatchupWeekHandler(week) {
    setCurMatchupPeriod(week);
    props.changeMatchupWeekHandler(week);
  }

  if (
    typeof allMatchups === "undefined" ||
    typeof curMatchupPeriod === "undefined"
  ) {
    return <p>loading...</p>;
  }
  return (
    <div>
      {allMatchups.map((week, index) => {
        if (String(week) === String(curMatchupPeriod)) {
          return (
            <button
              key={index + 2000}
              className={classes.activeMatchupButton}
              onClick={() => changeMatchupWeekHandler(week)}
            >
              {week}
            </button>
          );
        }
        return (
          <button
            key={index + 2000}
            className={classes.matchupButton}
            onClick={() => changeMatchupWeekHandler(week)}
          >
            {week}
          </button>
        );
      })}
    </div>
  );
}

export default MatchupPeriods;
