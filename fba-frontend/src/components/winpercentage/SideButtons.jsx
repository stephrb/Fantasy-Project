import React, { useState } from 'react';
import Card from '../ui/Card';
import classes from './SideButtons.module.css'
function SideButtons(props) {
  const [assessInjuries, setAssessInjuries] = useState(true);
  const [numGames, setNumGames] = useState(82);

  function refreshButtonhandler(assessInjuries, numGames) {
    props.refreshButtonhandler(assessInjuries, numGames)
  }
  return (
    <div style={{marginRight:'5rem', marginTop:'2rem'}}>
        <Card size='10'>
            <label>
            <input
            type="checkbox"
            checked={assessInjuries}
            onChange={(event) => setAssessInjuries(event.target.checked)}
            />
            Assess injuries
            </label>
            <div style={{width: '17rem'}}>
                <input 
                    type="range"
                    min={1}
                    max={82}
                    value={numGames}
                    onChange={(event) => {
                        setNumGames(event.target.value)
                    }}
                    style={{width: '13rem'}}
                    />
                    <label>{numGames}</label>
            </div>
            <button className={classes.actionsbutton} onClick={() => refreshButtonhandler(assessInjuries, numGames)}>
                Refresh Projections
            </button>
        </Card> 
    </div>
  );
}

export default SideButtons;