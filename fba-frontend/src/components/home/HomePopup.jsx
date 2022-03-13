import React, { useRef } from 'react';

import Card from '../ui/Card';
import classes from './HomePopup.module.css';
import Header from '../ui/Header';

function HomePopup(props) {
    const leagueId = useRef("");
    const isLoading = props.loading;
    const wasError = props.error;

    function submitHandler(event) {
        event.preventDefault();
        const leagueIdData = {leagueId : leagueId.current.value};
        props.createModel(leagueIdData)
    }

    function demoHandler(event) {
        event.preventDefault();
        props.createDemo()
    }
    
    return (
        
        <section className={classes.center}>
            <Card>
                <Header text="Enter your ESPN League ID to get started!" />
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
                {!isLoading && <button className= {classes.demo} onClick={demoHandler}>Demo</button>}
                {!isLoading && wasError && <p className={classes.inline}>The entered League ID could not be found.</p>}
            </Card>
        </section>
    );
}

export default HomePopup;