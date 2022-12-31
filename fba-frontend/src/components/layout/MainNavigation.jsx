import { Link, useLocation } from "react-router-dom";
import classes from "./MainNavigation.module.css";
import React from "react";

function MainNavigation() {
  const location = useLocation();

  return (
    <header className={classes.container}>
      <div className={classes.logo}>Fantasy Analytics</div>
      <nav>
        <ul>
          <li>
            <Link to="/" className={location.pathname === '/' ? classes.active : ''}>
              Home
            </Link>
          </li>
          <li>
            <Link to="/rankings" className={location.pathname === '/rankings' ? classes.active : ''}>
              Power Rankings
            </Link>
          </li>
          <li>
            <Link to="/compare" className={location.pathname === '/compare' ? classes.active : ''}>
              Compare Schedules
            </Link>
          </li>
          <li>
            <Link to="/playoff" className={location.pathname === '/playoff' ? classes.active : ''}>
              Playoff Machine
            </Link>
          </li>
          <li>
            <Link to="/projections" className={location.pathname === '/projections' ? classes.active : ''}>
              Matchup Projections
            </Link>
          </li>
          <li>
            <Link to="/nbagames" className={location.pathname === '/nbagames' ? classes.active : ''}>
              NBA Weekly Games
            </Link>
          </li>
          <li>
            <Link to="/winpercentage" className={location.pathname === '/winpercentage' ? classes.active : ''}>
              Win Probabilities
            </Link>
          </li>
        </ul>
      </nav>
    </header>
  );
}

export default MainNavigation;