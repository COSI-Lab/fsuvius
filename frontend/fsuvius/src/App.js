import './App.css';
import Navbar from './components/navbar';
import About from './pages/about';
import Home from './pages/home';
import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';

/* 
  App just returns the navbar and the elements it points to.
*/
function App() {
  return (
    <div>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" exact element={<Home />} />
          <Route path="/about" element={<About />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
