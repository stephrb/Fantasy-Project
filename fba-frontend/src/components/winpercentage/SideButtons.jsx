import React, { useState } from 'react';
import Card from '../ui/Card';
import classes from './SideButtons.module.css'
import Streaming from './Streaming';
function SideButtons(props) {
  const [assessInjuries, setAssessInjuries] = useState(true);
  const [numGames, setNumGames] = useState(82);

  function refreshButtonhandler(assessInjuries, numGames) {
    props.refreshButtonhandler(assessInjuries, numGames)
  }
  return (
    <div style={{marginRight:'5rem', marginTop:'1.4rem'}}>
        <Card size='10'>
          <div className={classes.default} style={{backgroundColor:"rgb(44, 42, 41)", fontSize:'medium', border:"rgb(44, 42, 41)"}}>Options</div>
          <div style={{display:"flex", alignItems:'center'}}>
            <p className={classes.default}>Exclude Injured Players</p>
              <label style={{alignItems:'center', justifyPositon:'center'}}>
                <input 
                type="checkbox"
                checked={assessInjuries}
                onChange={(event) => setAssessInjuries(event.target.checked)}
                style={{display:'none'}}
                />
                <div className={classes.toggle}></div>
                </label>
          </div>
          
            <div style={{width: '17rem'}}>
              <div style={{width:'10rem', fontSize:'11px'}} className={classes.default}>Calculate From Last <b style={{color:'#69c7ec'}}>{numGames}</b> {numGames === 1 ? 'game' : 'games'}
              </div>
              
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
  
            </div>
            <button className={classes.actionsbutton} onClick={() => refreshButtonhandler(assessInjuries, numGames)}>
                Refresh Projections
            </button>
        </Card>
        <Streaming 
          addStreamerHandler={props.addStreamerHandler}
          deleteStreamerHandler={props.deleteStreamerHandler}
          streamers={props.streamers}/>
    </div>
  );
}

export default SideButtons;