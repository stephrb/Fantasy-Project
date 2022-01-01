import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Layout from './components/layout/Layout';
import PowerRankings from './pages/PowerRankings';
import Home from './pages/Home';
import PlayoffMachine from './pages/PlayoffMachine';
import ScheduleComparison from './pages/ScheduleComparison';

function App() {
  
  return (
    
      <BrowserRouter>
        <Layout>
          <Routes>
          
            <Route path="" element={<Home />} />
            <Route path="/teams" element={<PowerRankings />} />
            <Route path="/compare" element={<ScheduleComparison />} />
            <Route path="/playoff" element={<PlayoffMachine />} />
          
          </Routes>
        </Layout>
      </BrowserRouter>
    
    
  );
}

export default App;
