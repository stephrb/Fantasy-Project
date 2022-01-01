import { Link } from 'react-router-dom';
import classes from './MainNavigation.module.css';
import React from 'react';
function MainNavigation() {

  return (
    <header className={classes.header}>
      <div className={classes.logo}>Fantasy ToolKit</div>
      <nav>
        <ul>
          <li>
            <Link to=''>Home</Link>
          </li>
          <li>
            <Link to='/teams'>Power Rankings</Link>
          </li>
          <li>
            <Link to='/compare'> Compare Schedules</Link>
          </li>
          <li>
            <Link to='/playoff'> Playoff Machine</Link>
          </li>
        </ul>
      </nav>
    </header>
  );
}

export default MainNavigation;