import React, { useEffect, useState } from "react";
import ModelService from "../../services/ModelService";
import Card from "../ui/Card";
import classes from "./NBATable.module.css";
function NBATable(props) {
  const [proTeams, setProTeams] = useState([]);

  useEffect(() => {
    ModelService.getProTeamGames(props.matchupPeriod).then((res) => {
      setProTeams(res.data);
    });
  }, [props.matchupPeriod]);

  if (typeof proTeams === "undefined") {
    return <p>loading...</p>;
  }
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
        </tbody>
      </table>
    </Card>
  );
}

export default NBATable;
