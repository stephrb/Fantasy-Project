import React, { useEffect, useState } from 'react';
import ModelService from '../../services/ModelService';
import axios from 'axios';
import classes from './DailyLineups.module.css'
// import Streaming from './Streaming'
function DailyLineups(props) {
    const [dailyLineups, setDailyLineups] = useState()
    const days = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"];
    const [matchupPeriod, teamId] = [props.matchupPeriod, props.teamId]
    const [refresh, setRefresh] = [props.refresh, props.setRefresh]
    useEffect(() => {
        const controller = new AbortController();
        const body = {
            "matchupPeriod": matchupPeriod,
            "assessInjuries": props.assessInjuries,
            "numRecentGames": props.numGames,
            "teamId": teamId
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
      }, [matchupPeriod, props.assessInjuries, props.numGames, teamId]);

      const handleChange = (playerId, day) => (event) => {
        const totalSelected = findTotalSelected(day)

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

        const [meanAdj, varAdj] = props.streamingSpots.map((streamer)=> 
                [Object.values(streamer.availability[teamId][matchupPeriod]).filter(Boolean).length * streamer.average,
                Object.values(streamer.availability[teamId][matchupPeriod]).filter(Boolean).length * streamer.std * streamer.std])
                .reduce((acc, [mean, std]) => {
                  acc[0] += mean;
                  acc[1] += std;
                  return acc;
                }, [0, 0]);
        const body = {
            "teamId": teamId,
            "numGames": props.numGames,
            "matchupPeriod": matchupPeriod,
            "dailyLineups": dailyLineups,
            "meanAdj":meanAdj,
            "varAdj":varAdj
        }

        ModelService.setDailyLineups(body, { signal: controller.signal })
        .then(() => setRefresh(!refresh))
        .catch((error) => {
            if (axios.isCancel(error)) {
              console.log("Request canceled", error.message);
            } else {
              console.log(error);
            }
          });
        props.handleClose()
    }

    function findTotalSelected(day) {
      let totalSelected = 0;
      if (dailyLineups) {
        totalSelected = Object.values(dailyLineups).reduce(
          (acc, availability) => acc + (availability[day] || 0),
          0
        );
      }

      totalSelected += props.streamingSpots.map((spot) => spot.availability[teamId][matchupPeriod][day]).reduce(
        (acc, availability) => acc + (availability || 0),
        0
      );
      return totalSelected
    }

    return (
        <div>
            <table className={classes.styledtable}>
          <thead>
            <tr>
              <th>Player</th>
              {days.map(weekday => (
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
                  {days.map(weekday => (
                    <td key={weekday}>
                    {weekday in availability && availability[weekday] ? (
                      <label>
                      <input style={{display:'none'}}className={classes.cell} type="checkbox" checked={availability[weekday]} onChange={handleChange(playerId, weekday)} />
                      <div className={classes.toggle}></div>
                      </label>
                    ) : weekday in availability ? (
                      <label>
                      <input style={{display:'none'}}className={classes.cell} type="checkbox" checked={availability[weekday]} onChange={handleChange(playerId, weekday)} />
                      <div className={classes.toggle}></div>
                      </label>
                    ) : (
                      <div>
                        
                      </div>
                    )}
                    </td>
                  ))}
                  <td>{Object.values(availability).filter(Boolean).length}</td>
                </tr>
              ))}
              {props.streamingSpots.map((streamer, index) => (
                <tr key={index}>
                  <td>Streamer ({streamer.average + ", " + streamer.std})</td>
                  {days.map(weekday => (
                    <td key={weekday}>
                    {weekday in streamer.availability[teamId][matchupPeriod] && streamer.availability[teamId][matchupPeriod][weekday] ? (
                      <label>
                      <input style={{display:'none'}}className={classes.cell} type="checkbox" checked={streamer.availability[teamId][matchupPeriod][weekday]} onChange={props.updateStreamers(index, weekday, teamId, findTotalSelected(weekday))} />
                      <div className={classes.toggle}></div>
                      </label>
                    ) : weekday in streamer.availability[teamId][matchupPeriod] ? (
                      <label>
                      <input style={{display:'none'}}className={classes.cell} type="checkbox" checked={streamer.availability[teamId][matchupPeriod][weekday]} onChange={props.updateStreamers(index, weekday, teamId, findTotalSelected(weekday))} />
                      <div className={classes.toggle}></div>
                      </label>
                    ) : (
                      <div>
                        
                      </div>
                    )}
                    </td>
                  ))}
                  <td>{Object.values(streamer.availability[teamId][matchupPeriod]).filter(Boolean).length}</td>
                </tr>
              ))}

            <tr>
              <td>Total</td>
              {days.map(weekday => (
                <td key={weekday}>
                  {dailyLineups &&
                    findTotalSelected(weekday)}
                </td>
              ))}
              <td>
          {dailyLineups &&
            days.map(weekday => (
              findTotalSelected(weekday)
            )).reduce((acc, cur) => acc + cur, 0)}
              </td>
            </tr>
          </tbody>
        </table>
        <button className={classes.actionsbutton}onClick={handleClose}>Submit</button>
        </div>
      );
}


export default DailyLineups;