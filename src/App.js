import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Layout from './components/layout/Layout';
import PowerRankings from './pages/PowerRankings';
import Home from './pages/Home';
import PlayoffMachine from './pages/PlayoffMachine';
import Comparison from './pages/Comparison';
import MatchupProjections from './pages/MatchupProjections';

function App() {
  
  return (
    
      <BrowserRouter>
        <Layout>
          <Routes>
          
            <Route path="" element={<Home />} />
            <Route path="/rankings" element={<PowerRankings />} />
            <Route path="/compare" element={<Comparison />} />
            <Route path="/playoff" element={<PlayoffMachine />} />
            <Route path="/projections" element={<MatchupProjections />} />

          </Routes>
        </Layout>
      </BrowserRouter>
    
    
  );
}

export default App;
