import React, { useState } from 'react';
import { Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import Dashboard from "./pages/Dashboard";
import ResultsPage from "./pages/ResultsPage";
import AddField from './pages/AddField';
import Summary from "./pages/Summary";





function App() {
  const [step, setStep] = useState(1);
  const [results, setResults] = useState([]);
  return (
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/dashboard" element={<Dashboard />} />
      <Route path="/results" element={<ResultsPage />} />
      <Route path="/add-field" element={<AddField />} />
      <Route path="/results" element={<ResultsPage results={results} setStep={setStep} />} />
      <Route path="/summary" element={<Summary />} />




    </Routes>
  );
}

export default App;
