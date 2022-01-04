import React, { useEffect, useState } from 'react';
import ModelService from '../services/ModelService';
import MatchupOutcomes from '../components/playoffmachine/MatchupOutcomes';
import PlayoffRankings from '../components/playoffmachine/PlayoffRankings';
import Header from '../components/ui/Header';
function PlayoffMachine(props) {
    const [playoffRankings, setPlayoffRankings] = useState([]);
    const [isSorted, setIsSorted] = useState(true);

    useEffect(() => {
        ModelService.getIsSorted().then((res) => {
            setIsSorted(res.data);
        }).catch();
        ModelService.getPlayoffRankings().then((res) => {
            setPlayoffRankings(res.data);
        }).catch();
        ModelService.getIsSorted().then((res) => {
            setIsSorted(res.data);
        }).catch();
     }, [isSorted]);

     function handleSetOutcome() {
        setIsSorted(!isSorted);
     }

     if (typeof playoffRankings === 'undefined') {
        return (
            <p>
                loading...
            </p>
        )
    }
    return (
        <div>
            <Header text="Playoff Machine" />
            <PlayoffRankings playoffRankings={playoffRankings} isSorted={isSorted}/>
            <MatchupOutcomes handleSetOutcome={handleSetOutcome}/>
        </div>
    );
}

export default PlayoffMachine;