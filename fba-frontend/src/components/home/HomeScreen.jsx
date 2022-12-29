import React from "react";
import HomePreview from "./HomePreview";
import classes from "./HomeScreen.module.css";

function HomeScreen(props) {
  return (
    <section className={classes.width}>
      <ul className={classes.list}>
        <li>
          <HomePreview
            link="rankings"
            title="Power Rankings"
            text="Generates Power Rankings for each team based on total points scored, 
                                                                              winning percentage, and a comparison between points scored and the median 
                                                                              points scored of a given week."
          />
        </li>
        <li>
          <HomePreview
            link="compare"
            title="Compare Schedules"
            text="Displays the hypothetical record of each team for each other team's 
                                                                                schedule and additionally shows what the record of each team would be 
                                                                                if they played every other team each week."
          />
        </li>
        <li>
          <HomePreview
            link="playoff"
            title="Playoff Machine"
            text="Allows the user to select outcomes of future matchups and displays the 
                                                                              playoff standings from the results of the outcomes of already played matchups
                                                                              and inputted results."
          />
        </li>
        <li>
          <HomePreview
            link="projections"
            title="Matchup Projections"
            text="Displays the projected score for each team in a given matchup based on
                                                                                      the number of games that a player has left in the week multiplied by their 
                                                                                      average score over a given time period."
          />
        </li>
        <li>
          <HomePreview
            link="nbagames"
            title="NBA Weekly Games"
            text="Displays the number of games each NBA team has each week and which days each team has
                                                                                each game so that it is easy to identify optimal players to pick up off the waiver wire
                                                                                each week"
          />
        </li>
      </ul>
      <footer>
        <button className={classes.actionsbutton} onClick={props.handlePopup}>
          <h1 className={classes.header}>Change League </h1>
        </button>
      </footer>
    </section>
  );
}

export default HomeScreen;
