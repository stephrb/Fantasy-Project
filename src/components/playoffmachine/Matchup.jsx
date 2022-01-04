import React, { useEffect, useState } from 'react';
import classes from './Matchup.module.css';

function Matchup(props) {

    const [homeTeamWinner, setHomeTeamWinner] = useState(false);
    const [awayTeamWinner, setAwayTeamWinner] = useState(false);
    const [tieWinner, setTieWinner] = useState(false);
    useEffect(() => {
        setHomeTeamWinner(props.matchup.winnerTeamId === props.matchup.homeTeamId);
        setAwayTeamWinner(props.matchup.winnerTeamId === props.matchup.awayTeamId);
        setTieWinner(props.isTie);
    }, [])

    function setWinnerHomeHandler() {
        setHomeTeamWinner(true);
        setAwayTeamWinner(false);
        setTieWinner(false);
        props.setWinnerHomeHandler(props.matchup)
    }

    function setWinnerAwayHandler() {
        setAwayTeamWinner(true);
        setHomeTeamWinner(false);
        setTieWinner(false);
        props.setWinnerAwayHandler(props.matchup)
    }

    function setWinnerTieHandler() {
        setHomeTeamWinner(false);
        setAwayTeamWinner(false);
        setTieWinner(true);
        props.setWinnerTieHandler(props.matchup);
    }

    return (
        <ul className={classes.container}>
            <button className={homeTeamWinner ? classes.winner : awayTeamWinner ? classes.loser : classes.default} onClick={setWinnerHomeHandler}>{props.matchup.homeTeamName}</button>
            <button className={homeTeamWinner ? classes.default : awayTeamWinner ? classes.default : tieWinner  ? classes.tie : classes.default}onClick={setWinnerTieHandler}>Tie</button>
            <button className={homeTeamWinner ? classes.loser : awayTeamWinner ? classes.winner : classes.default} onClick={setWinnerAwayHandler}>{props.matchup.awayTeamName}</button>
        </ul>
    );
}

export default Matchup;