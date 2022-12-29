import React, { useEffect, useState } from "react";
import ModelService from "../../services/ModelService";
import classes from "./MatchupPeriodButtons.module.css";
import axios from 'axios'

function MatchupPeriodButtons(props) {
  const [allMatchups, setAllMatchups] = useState();
  const [curMatchupPeriod, setCurMatchupPeriod] = useState(props.matchupPeriod);

  useEffect(() => {
    const controller = new AbortController();

    ModelService.getAllMatchups({ signal: controller.signal }).then((res) => {
      setAllMatchups(res.data);
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
        if (Number(week) === Number(curMatchupPeriod)) {
          return (
            <button
              key={index + 1000}
              className={classes.activeMatchupButton}
              onClick={() => changeMatchupWeekHandler(week)}
            >
              {week}
            </button>
          );
        }
        return (
          <button
            key={index + 1000}
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

export default MatchupPeriodButtons;
