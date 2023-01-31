import React from 'react';
import {Link} from 'react-router-dom';
import '../App.css';

function Navbar() {
    return (
      <div className="inline_menu">
        <div className="inline_text">Mount Fsuvius</div>
        <div className="inline_button">
            <Link to="/">Home</Link>
        </div>
        <div className="inline_button">
            <Link to="/create">Create Account</Link>
        </div>
        
      </div>
    );
  }
  
export default Navbar;