import React, { useState, useEffect } from "react";
import ModelService from "../../services/ModelService";
import Card from "../ui/Card";
import classes from "./ComparisonTable.module.css";
import axios from 'axios'

function WeeklyComparison(props) {
  const [teamRecords, setTeamRecords] = useState();

  useEffect(() => {
    const controller = new AbortController();

    ModelService.getWeeklyComparison({ signal: controller.signal }).then((res) => {
      setTeamRecords(res.data);
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

  if (typeof teamRecords === "undefined") {
    return <p>loading...</p>;
  }
  return (
    <Card>
      <table className={classes.styledtable}>
        <thead>
          <tr>
            <th>Team Name</th>
            {teamRecords[0].records.map((record, index) => {
              if (teamRecords[0].records.length - 1 !== index) {
                return <th key={index + 1}>{index + 1}</th>;
              }

              return <th key={index + 1}>Total</th>;
            })}
          </tr>
        </thead>
        <tbody>
          {teamRecords.map((teamRecord) => (
            <tr key={teamRecord.teamName[0]}>
              <td>
                <b>{teamRecord.teamName[0]}</b>
              </td>
              {teamRecord.records.map((record, index) => (
                <td key={index}>
                  {record.split("-")[2] === "0" ? record.split("-").slice(0, 2).join("-") : record}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </Card>
  );
}

export default WeeklyComparison;
