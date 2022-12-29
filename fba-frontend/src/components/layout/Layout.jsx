import MainNavigation from "./MainNavigation";
import classes from "./Layout.module.css";
import React from "react";
function Layout(props) {
  return (
    <div className={classes.container1}>
      <div className={classes.container2}>
        <MainNavigation />
        <main className={classes.main}>{props.children}</main>
      </div>
    </div>
  );
}

export default Layout;
