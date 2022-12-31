import React, { useState, useEffect } from "react";
import ModelService from "../../services/ModelService";
import Card from "../ui/Card";
import classes from "./PowerRankingsTable.module.css";
import axios from "axios";

function PowerRankingsTable(props) {
  const [teams, setTeams] = useState([]);

  useEffect(() => {
    const controller = new AbortController();

    ModelService.getPowerRankings({ signal: controller.signal })
      .then((res) => {
        setTeams(res.data);
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
  }, []);

  if (typeof teams === "undefined") {
    return <p>loading...</p>;
  }
  return (
    <Card>
      <table className={classes.styledtable}>
        <thead>
          <tr>
            <th scope="col">Team Name</th>
            <th scope="col">Record</th>
            <th scope="col">Power Score</th>
          </tr>
        </thead>
        <tbody>
          {teams.map((team) => (
            <tr key={team.powerRankingScore}>
              <td>
                <b>{team.name}</b>
              </td>
              <td> {team.record}</td>
              <td> {team.powerRankingScore}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <p className={classes.footer}>
        Calulated by (Points Scored + (Points Scored * Winning %) + (Points
        Scored vs the median score of the week)) / (number of weeks) + (total team average in last 30 days)
      </p>
    </Card>
  );
}

export default PowerRankingsTable;
