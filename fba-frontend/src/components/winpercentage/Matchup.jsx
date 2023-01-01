import React, { useState } from 'react';
import DailyLineups from './DailyLineups';
import Card from '../ui/Card';
import './Matchup.css'
import Backdrop from '../ui/Backdrop';
import Header from '../ui/Header'

function Matchup(props) {
  const [showDailyLineups, setShowDailyLineups] = useState(false);
  const [dailyLineupsTeamId, setDailyLineupsTeamId] = useState();

  function handleHomeTeamClick() {
    setShowDailyLineups(true);
    setDailyLineupsTeamId(props.homeTeamId)
  }

  function handleAwayTeamClick() {
    setShowDailyLineups(true);
    setDailyLineupsTeamId(props.awayTeamId)
  }

  function getGreenToRed(percent){
      let r = percent<50 ? 255 : Math.floor(255-(percent*2-100)*255/100);
      let g = percent>50 ? 255 : Math.floor((percent*2)*255/100);
      r /= 1.5
      g /= 1.5
      return 'rgb('+r+','+g+',0)';
  }

  const homeTeamWinPercentage = (props.homeTeamWinPercentage * 100).toFixed(2);
  const awayTeamWinPercentage = (props.awayTeamWinPercentage * 100).toFixed(2);

  const homeTeamColor = getGreenToRed(homeTeamWinPercentage);
  const awayTeamColor = getGreenToRed(awayTeamWinPercentage);

  const handleClose = () => {
    setShowDailyLineups(false);
    props.handleClose();
    };
  
  return (
    <div className="matchup-container">

        {showDailyLineups ? (
          <Backdrop>
            <div style={{display: "flex", justifyContent:"center", alignItems:"center", marginTop:"5rem"}}>
              <Card>
                <Header text='Set Lineups'></Header>
                <DailyLineups
                matchupPeriod={props.matchupPeriod} 
                assessInjuries={props.assessInjuries} 
                numGames={props.numGames}
                teamId={dailyLineupsTeamId}
                showDailyLineups={showDailyLineups}
                handleClose={handleClose}
                />
              </Card>
            </div>
            
           
          </Backdrop>
          
        ) : (
          <Card size={"1% 10"}>
          <ul className="matchup-list">
            <li className="matchup-percentage" style={{ width: '15%', backgroundColor: homeTeamColor }}>{homeTeamWinPercentage}%</li>
            <li className="matchup-button-container" style={{ minWidth: '200px' }}>
              <button className="matchup-button" onClick={handleHomeTeamClick}>{props.homeTeamName}</button>
            </li>
            <li className="matchup-vs">vs</li>
            <li className="matchup-button-container" style={{ minWidth: '200px' }}>
              <button className="matchup-button" onClick={handleAwayTeamClick}>{props.awayTeamName}</button>
            </li>
            <li className="matchup-percentage" style={{ width: '15%', backgroundColor: awayTeamColor }}>{awayTeamWinPercentage}%</li>
          </ul>
          </Card>
        )}

    </div>
  );
}

export default Matchup;
  