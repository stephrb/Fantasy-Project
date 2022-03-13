import React, { useEffect, useState } from 'react';
import ModelService from '../../services/ModelService';
import classes from './WeekSelector.module.css';

function WeekSelector(props) {
    const [remMatchupList, setRemMatchupList] = useState();
    const [curMatchupPeriod, setCurMatchupPeriod] = useState();

    useEffect(() => {
        ModelService.getRemainingMatchupPeriods().then((res) => {
            setRemMatchupList(res.data);
            setCurMatchupPeriod(res.data[0]);
        });
     }, []);

     function changeMatchupWeekHandler(week) {
        setCurMatchupPeriod(week);
        props.changeMatchupWeekHandler(week);
    }

    if (typeof remMatchupList === 'undefined' || typeof curMatchupPeriod === 'undefined') {
        return (
            <p>
                loading...
            </p>
        )
    }
    return (
        <div>
             {
                    remMatchupList.map(
                        (week, index) => {
                        if (week === curMatchupPeriod) {
                            return <button key={index + 2000} className={classes.activeMatchupButton} onClick={() => changeMatchupWeekHandler(week)} >
                                        {week}
                                    </button>
                        }
                         return <button key={index + 2000} className={classes.matchupButton} onClick={() => changeMatchupWeekHandler(week)} >
                                    {week}
                                </button>
                        }
                    )
                }
        </div>
    );
}

export default WeekSelector;