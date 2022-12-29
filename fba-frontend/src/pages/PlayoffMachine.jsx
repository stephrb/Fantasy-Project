import React, { useEffect, useState } from "react";
import ModelService from "../services/ModelService";
import MatchupOutcomes from "../components/playoffmachine/MatchupOutcomes";
import PlayoffRankings from "../components/playoffmachine/PlayoffRankings";
import Header from "../components/ui/Header";
import axios from 'axios'

function PlayoffMachine(props) {
  const [playoffRankings, setPlayoffRankings] = useState([]);
  const [isSorted, setIsSorted] = useState(true);

  useEffect(() => {
    const controller = new AbortController();
    
    ModelService.getIsSorted({ signal: controller.signal })
      .then((res) => {
        setIsSorted(res.data);
      })
      .catch((error) => {
        if (axios.isCancel(error)) {
          console.log("Request canceled", error.message);
        } else {
          console.log(error);
        }
      });

    ModelService.getPlayoffRankings({ signal: controller.signal })
      .then((res) => {
        setPlayoffRankings(res.data);
      })
      .catch((error) => {
        if (axios.isCancel(error)) {
          console.log("Request canceled", error.message);
        } else {
          console.log(error);
        }
      });

    ModelService.getIsSorted({ signal: controller.signal })
      .then((res) => {
        setIsSorted(res.data);
      })
      .catch((error) => {
        if (axios.isCancel(error)) {
          console.log("Request canceled", error.message);
        } else {
          console.log(error);
        }
      });

      return () => {
        controller.abort();
      };
  }, [isSorted]);

  function handleOutcomeChange() {
    setIsSorted(!isSorted);
  }

  if (typeof playoffRankings === "undefined") {
    return <p>loading...</p>;
  }
  return (
    <div>
      <Header text="Playoff Machine" />
      <PlayoffRankings playoffRankings={playoffRankings} isSorted={isSorted} />
      <MatchupOutcomes handleOutcomeChange={handleOutcomeChange} />
    </div>
  );
}

export default PlayoffMachine;
