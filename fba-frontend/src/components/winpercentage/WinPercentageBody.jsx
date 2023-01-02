import React, {useState} from 'react';
import SideButtons from './SideButtons';
import MatchupList from './MatchupList';

function WinPercentageBody(props) {
  const [assessInjuries, setAssessInjuries] = useState(true);
  const [numGames, setNumGames] = useState(82);
  const [streamingSpots, setStreamingSpots] = useState([])
  const [teamIdList, setTeamIdList] = useState([])
  const [reset, setReset] = useState(false)
  const [refresh, setRefresh] = useState(true)

  const matchupPeriod = props.matchupPeriod
  const days = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"];
  
  function refreshButtonhandler(assessInjuries, numGames) {
    setAssessInjuries(assessInjuries)
    setNumGames(numGames)
    resetStreamingSpots()
    setReset(true)
  }

  function addStreamerHandler(average, std) {
    const date = new Date();
    const today = new Date(date.setHours(date.getHours() - 4));
    let dayOfWeek = today.getDay();
    if (dayOfWeek === 0) dayOfWeek = 7
    dayOfWeek -= 1
    const availability = {};
    for (let k = 0; k < teamIdList.length; k++) {
      const id = teamIdList[k]
      availability[id] = {}
      for (let i = 0; i < props.allMatchups.length; i++) {
        const p = props.allMatchups[i]
        availability[id][p] = {}
        if (Number(i) === 0) {
          for (let j = dayOfWeek; j < days.length; j++) {
            availability[id][p][days[j]] = false
          }
        } else {
          for (let j = 0; j < days.length; j++) {
                availability[id][p][days[j]] = false
              }
        }
      }
    }



    setStreamingSpots([...streamingSpots, { 
      average: average, 
      std: std,
      availability: availability
    }]);
  }

  function deleteStreamerHandler() {
    const temp = [...streamingSpots];
    temp.pop();
    setStreamingSpots(temp);
    setReset(true)
  }

  const updateStreamers = (index, day, teamId, totalSelected) => (event) => {
    if (totalSelected >= 10 && event.target.checked) {
      alert("There can only be 10 players selected per day.");
      event.preventDefault();
      return;
    }

    const newAvailability = { ...streamingSpots[index] };
    newAvailability.availability[teamId][matchupPeriod][day] = event.target.checked;
    setStreamingSpots(streamingSpots.map((spot, i) => (i === index ? newAvailability : spot)));
  };

  function resetStreamingSpots() {
    // const stats = streamingSpots.map((streamer) => [streamer.average, streamer.std])
    setStreamingSpots([])
  }

    return (
      <div style={{ display: 'flex', justifyContent: 'center', marginRight:'25rem'}}>
        <SideButtons 
          addStreamerHandler={addStreamerHandler} 
          deleteStreamerHandler={deleteStreamerHandler} 
          refreshButtonhandler={refreshButtonhandler}
          streamers={streamingSpots.length}
          />
          
        <MatchupList 
          setTeamIdList={setTeamIdList}
          updateStreamers={updateStreamers} 
          streamingSpots={streamingSpots} 
          matchupPeriod={props.matchupPeriod} 
          numGames={numGames} 
          reset={reset}
          setReset={setReset}
          refresh={refresh}
          setRefresh={setRefresh}
          assessInjuries={assessInjuries} />
      </div>
    );
  }
  
  export default WinPercentageBody;