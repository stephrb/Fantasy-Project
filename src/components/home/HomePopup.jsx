import React, { useRef, useEffect } from 'react';

import Card from '../ui/Card';
import classes from './HomePopup.module.css';

function HomePopup(props) {
    const leagueId = useRef();
    const isLoading = props.loading;
    const wasError = props.error;

    function submitHandler(event) {
        event.preventDefault();
        const leagueIdData = {leagueId : leagueId.current.value};
        props.createModel(leagueIdData)
        
    }
    

    return (
        
        <Card>
            <h1 className={classes.header}>Enter your ESPN League ID to get started!</h1>
            <div className={classes.control}>
                {!isLoading 
                    ? <input className={classes.textbox} placeholder="League ID" 
                        type="text"
                        ref={leagueId}
                        />
                    : <div className={classes.loader}></div>
                }
            </div>
            {!isLoading && <button className={classes.actionsbutton} onClick={submitHandler}>Submit</button>}
            {!isLoading && wasError && <a className={classes.inline}>The entered League ID could not be found.</a>}
            
        </Card>
    );
}

export default HomePopup;