import React, { useEffect, useState } from 'react';
import ModelService from '../../services/ModelService';
import classes from './MatchupPeriodButtons.module.css';

function MatchupPeriodButtons(props) {
    const [allMatchups, setAllMatchups] = useState();
    const [curMatchupPeriod, setCurMatchupPeriod] = useState();

    useEffect(() => {
        ModelService.getAllMatchups().then((res) => {
            setAllMatchups(res.data);
        });
        ModelService.getCurrentMatchupPeriod().then((res) => {
            setCurMatchupPeriod(res.data)
        });
     }, []);

     function changeMatchupWeekHandler(week) {
        setCurMatchupPeriod(week);
        props.changeMatchupWeekHandler(week);
    }
    
     if (typeof allMatchups === 'undefined' || typeof curMatchupPeriod === 'undefined') {
        return (
            <p>
                loading...
            </p>
        )
    }
    return (
        <div>
             {
                    allMatchups.map(
                        (week, index) => {
                        if (week === curMatchupPeriod) {
                            return <button key={index + 1000} className={classes.activeMatchupButton} onClick={() => changeMatchupWeekHandler(week)} >
                                        {week}
                                    </button>
                        }
                         return <button key={index + 1000} className={classes.matchupButton} onClick={() => changeMatchupWeekHandler(week)} >
                                    {week}
                                </button>
                        }
                    )
                }
        </div>
    );
}

export default MatchupPeriodButtons;