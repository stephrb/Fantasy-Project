import React from 'react';
import classes from './SideButtons.module.css';
function SideButtons(props) {

    function setTimePeriodHandler(timePeriod) {
        props.setTimePeriodHandler(timePeriod);
    }

    function toggleAssessInjuriesHandler() {
        props.toggleAssessInjuriesHandler();
    }

    return (
        <ul className={classes.container}>
            <li>
                <button className={props.timePeriod==="Season_2023" ? classes.activeButton : classes.default} onClick={() => setTimePeriodHandler("Season_2023")}>Season</button>
            </li>
            <li>
                <button className={props.timePeriod==="Last_30_2023" ? classes.activeButton : classes.default} onClick={() => setTimePeriodHandler("Last_30_2023")}>Last 30</button>
            </li>
            <li>
                <button className={props.timePeriod==="Last_15_2023" ? classes.activeButton : classes.default} onClick={() => setTimePeriodHandler("Last_15_2023")}>Last 15</button>
            </li>
            <li>
                <button className={props.timePeriod==="Last_7_2023" ? classes.activeButton : classes.default} onClick={() => setTimePeriodHandler("Last_7_2023")}>Last 7</button>
            </li>
            <li>
                <button className={props.assessInjuries===true ? classes.activeInjuries : classes.default} onClick={toggleAssessInjuriesHandler}>Affected by Injuries</button>
            </li>
        </ul>
    );
}

export default SideButtons;