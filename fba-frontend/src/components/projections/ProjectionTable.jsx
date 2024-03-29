import React, { useState, useEffect } from "react";
import ModelService from "../../services/ModelService";
import classes from "./ProjectionTable.module.css";
import axios from 'axios'

function ProjectionTable(props) {
  const [scores, setScores] = useState([]);

  useEffect(() => {
    const controller = new AbortController();

    ModelService.getProjectedScores(
      props.timePeriod,
      props.matchupPeriod,
      props.assessInjuries,
      { signal: controller.signal }
    ).then((res) => {
      setScores(res.data);
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
  }, [props.timePeriod, props.assessInjuries, props.matchupPeriod]);

  if (typeof scores === "undefined") {
    return <p>loading...</p>;
  }
  return (
    <div>
      <table className={classes.styledtable}>
        <thead>
          <tr>
            <th scope="col">Team Name</th>
            <th scope="col">Projected Points</th>
            <th scope="col">Games Left</th>
          </tr>
        </thead>
        <tbody>
          {scores.map((score) => (
            <tr key={score.teamName}>
              <td>
                <b>{score.teamName}</b>
              </td>
              <td> {score.points}</td>
              <td> {score.totalGames}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ProjectionTable;
