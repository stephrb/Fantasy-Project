import React, { useEffect, useState } from "react";
import ModelService from "../../services/ModelService";
import Card from "../ui/Card";
import classes from "./NBATable.module.css";
import axios from 'axios'

function NBATable(props) {
  const [proTeams, setProTeams] = useState([]);

  useEffect(() => {
    const controller = new AbortController();

    ModelService.getProTeamGames(props.matchupPeriod, { signal: controller.signal }).then((res) => {
      setProTeams(res.data);
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
  }, [props.matchupPeriod]);

  if (typeof proTeams === "undefined") {
    return <p>loading...</p>;
  }

  const totalGamesByDay = proTeams.reduce((total, team) => {
    team.games.forEach((game, index) => {
      total[index] = (total[index] || 0) + (game ? 1 : 0);
    });
    return total;
  }, []);

  const totalGames = totalGamesByDay.reduce((total, games) => total + games, 0);

  return (
    <Card>
      <table className={classes.styledtable}>
        <thead>
          <tr>
            <th>Team Name</th>
            <th>M</th>
            <th>Tu</th>
            <th>W</th>
            <th>Th</th>
            <th>F</th>
            <th>S</th>
            <th>S</th>
            <th># of Games</th>
          </tr>
        </thead>
        <tbody>
          {proTeams.map((team, index) => (
            <tr key={team.teamName}>
              <td>
                <b>{team.teamName}</b>
              </td>
              {team.games.map((game, index) => (
                <td
                  key={index}
                  className={game ? classes.hasGame : classes.noGame}
                ></td>
              ))}
              <td>{team.count}</td>
            </tr>
          ))}
          <tr>
          <td>Total Games</td>
          {totalGamesByDay.map((total, index) => (
            <td key={index}>{total}</td>
          ))}
          <td>{totalGames}</td>
        </tr>
        </tbody>
      </table>
    </Card>
  );
}

export default NBATable;
