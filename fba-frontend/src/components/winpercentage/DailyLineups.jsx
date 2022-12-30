import React, { useEffect, useState } from 'react';
import ModelService from '../../services/ModelService';
import axios from 'axios';
import classes from './DailyLineups.module.css'
function DailyLineups(props) {
    const [dailyLineups, setDailyLineups] = useState()
    const weekdays = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"];

    useEffect(() => {
        const controller = new AbortController();
        const body = {
            "matchupPeriod": props.matchupPeriod,
            "assessInjuries": props.assessInjuries,
            "numRecentGames": props.numGames,
            "teamId": props.teamId
        }
        ModelService.getDailyLineups(body, { signal: controller.signal }).then((res) =>
        setDailyLineups(res.data)
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
      }, [props.matchupPeriod, props.assessInjuries, props.numGames, props.teamId]);

      const handleChange = (playerId, day) => (event) => {
        const totalSelected = Object.values(dailyLineups).reduce(
            (acc, availability) => acc + (availability[day] || 0),
            0
          );
          if (totalSelected >= 10 && event.target.checked) {
            alert("There can only be 10 players selected per day.");
            event.preventDefault();
            return;
          }

        const newAvailability = { ...dailyLineups[playerId] };
        newAvailability[day] = event.target.checked;
        setDailyLineups({ ...dailyLineups,[playerId]: newAvailability });

    };

    function handleClose() {
        const controller = new AbortController();
        const body = {
            "teamId": props.teamId,
            "numGames": props.numGames,
            "matchupPeriod": props.matchupPeriod,
            "dailyLineups": dailyLineups
        }

        ModelService.setDailyLineups(body, { signal: controller.signal }).catch((error) => {
            if (axios.isCancel(error)) {
              console.log("Request canceled", error.message);
            } else {
              console.log(error);
            }
          });
        props.handleClose()
    }

    return (
        <div>
            <table className={classes.styledtable}>
          <thead>
            <tr>
              <th>Player</th>
              {weekdays.map(weekday => (
                <th key={weekday}>{weekday}</th>
              ))}
              <th>Total</th>
            </tr>
          </thead>
          <tbody>
            {dailyLineups &&
              Object.entries(dailyLineups).map(([playerId, availability]) => (
                <tr key={playerId}>
                  <td>{playerId.split(":")[1]}</td>
                  {weekdays.map(weekday => (
                    <td key={weekday}>
                    {weekday in availability && availability[weekday] ? (
                    <td >
                        <input className={classes.cell} type="checkbox" checked={availability[weekday]} onChange={handleChange(playerId, weekday)} />
                    </td>
                    ) : weekday in availability ? (
                    <td >
                        <input className={classes.cell} type="checkbox" checked={availability[weekday]} onClick={handleChange(playerId, weekday)} />
                    </td>
                    ) : (
                    <td>
                    </td>
                    )}
                    </td>
                  ))}
                  <td>{Object.values(availability).filter(Boolean).length}</td>
                </tr>
              ))}
            <tr>
              <td>Total</td>
              {weekdays.map(weekday => (
                <td key={weekday}>
                  {dailyLineups &&
                    Object.values(dailyLineups).reduce(
                      (acc, availability) => acc + (availability[weekday] || 0),
                      0
                    )}
                </td>
              ))}
              <td>
          {dailyLineups &&
            Object.values(dailyLineups).reduce(
              (acc, availability) => acc + Object.values(availability).filter(Boolean).length,0)}
              </td>
            </tr>
          </tbody>
        </table>
        <button className={classes.actionsbutton}onClick={handleClose}>Submit</button>
        </div>
        
      );
}


export default DailyLineups;