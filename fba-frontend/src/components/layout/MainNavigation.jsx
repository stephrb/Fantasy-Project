import { Link } from "react-router-dom";
import classes from "./MainNavigation.module.css";
import React from "react";
function MainNavigation() {
  return (
    <header className={classes.container}>
      <div className={classes.logo}>Fantasy Analytics</div>
      <nav>
        <ul>
          <li>
            <Link to="">Home</Link>
          </li>
          <li>
            <Link to="/rankings">Power Rankings</Link>
          </li>
          <li>
            <Link to="/compare">Compare Schedules</Link>
          </li>
          <li>
            <Link to="/playoff">Playoff Machine</Link>
          </li>
          <li>
            <Link to="/projections">Matchup Projections</Link>
          </li>
          <li>
            <Link to="/nbagames">NBA Weekly Games</Link>
          </li>
          <li>
            <Link to="/winpercentage">Win Probabilities</Link>
          </li>
        </ul>
      </nav>
    </header>
  );
}

export default MainNavigation;
