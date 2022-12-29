import React from "react";
import PowerRankingsTable from "../components/rankings/PowerRankingsTable";
import Header from "../components/ui/Header";
function PowerRankings(props) {
  return (
    <div>
      <Header text="Power Rankings"></Header>
      <PowerRankingsTable />
    </div>
  );
}

export default PowerRankings;
