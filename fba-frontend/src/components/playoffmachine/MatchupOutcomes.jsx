import React, { useEffect, useState } from "react";
import ModelService from "../../services/ModelService";
import classes from "./MatchupOutcomes.module.css";
import MatchupWeek from "./MatchupWeek";
import axios from 'axios'

function MatchupOutcomes(props) {
  const [remMatchupList, setRemMatchupList] = useState();
  const [curMatchupPeriod, setCurMatchupPeriod] = useState();
  const [isReset, setIsReset] = useState(false);
  useEffect(() => {
    const controller = new AbortController();

    ModelService.getRemainingMatchupPeriods({ signal: controller.signal }).then((res) => {
      setRemMatchupList(res.data);
      setCurMatchupPeriod(res.data[0]);
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
  }

  function resetPlayoffMachineHandler() {
    ModelService.resetPlayoffMachine();
    setIsReset(!isReset);
    props.handleOutcomeChange();
  }

  if (
    typeof remMatchupList === "undefined" ||
    typeof curMatchupPeriod === "undefined"
  ) {
    return <p>loading...</p>;
  }
  return (
    <div>
      <div>
        {remMatchupList.map((week, index) => {
          if (week === curMatchupPeriod) {
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
      <MatchupWeek
        week={curMatchupPeriod}
        reset={isReset}
        handleOutcomeChange={props.handleOutcomeChange}
      />
      <button
        className={classes.matchupButton}
        onClick={resetPlayoffMachineHandler}
      >
        Reset
      </button>
    </div>
  );
}

export default MatchupOutcomes;
