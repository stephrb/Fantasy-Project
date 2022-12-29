import React from "react";
import Card from "../ui/Card";
import classes from "./HomePreview.module.css";
import { Link } from "react-router-dom";

function HomePreview(props) {
  return (
    <div className={classes.item}>
      <Card>
        <div className={classes.content}>
          <Link to={props.link}>
            <h1 className={classes.header}>{props.title}</h1>
          </Link>
          <p>{props.text}</p>
        </div>
      </Card>
    </div>
  );
}

export default HomePreview;
