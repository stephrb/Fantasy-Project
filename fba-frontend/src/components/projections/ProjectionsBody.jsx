import React from "react";
import SideButtons from "./SideButtons";
import ProjectionTable from "./ProjectionTable";
import Card from "../ui/Card";
import classes from "./ProjectionsBody.module.css";
function ProjectionsBody(props) {
  return (
    <Card>
      <ul className={classes.container}>
        <li className={classes.table}>
          <ProjectionTable
            assessInjuries={props.assessInjuries}
            timePeriod={props.timePeriod}
            matchupPeriod={props.matchupPeriod}
          />
        </li>
        <li>
          <SideButtons
            setTimePeriodHandler={props.setTimePeriodHandler}
            toggleAssessInjuriesHandler={props.toggleAssessInjuriesHandler}
            assessInjuries={props.assessInjuries}
            timePeriod={props.timePeriod}
          />
        </li>
      </ul>
    </Card>
  );
}

export default ProjectionsBody;
