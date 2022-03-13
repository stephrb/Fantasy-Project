import React from 'react';
import Header from '../components/ui/Header';
import ScheduleComparison from '../components/comparison/ScheduleComparison';
import WeeklyComparison from '../components/comparison/WeeklyComparison';
function Comparison(props) {
    return (
        <div>
            <Header text="Schedule Comparison" />
            <ScheduleComparison />
            <WeeklyComparison />
        </div>
    );
}

export default Comparison;