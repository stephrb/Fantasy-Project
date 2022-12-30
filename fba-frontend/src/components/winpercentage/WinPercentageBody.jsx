import React, {useState} from 'react';
import SideButtons from './SideButtons';
import MatchupList from './MatchupList';

function WinPercentageBody(props) {
  const [assessInjuries, setAssessInjuries] = useState(true);
  const [numGames, setNumGames] = useState(82);

  function refreshButtonhandler(assessInjuries, numGames) {
    setAssessInjuries(assessInjuries)
    setNumGames(numGames)
  }
    return (
      <div style={{ display: 'flex', justifyContent: 'center', marginRight:'25rem'}}>
        <SideButtons refreshButtonhandler={refreshButtonhandler}/>
        <MatchupList matchupPeriod={props.matchupPeriod} numGames={numGames} assessInjuries={assessInjuries} />
      </div>
    );
  }
  
  export default WinPercentageBody;