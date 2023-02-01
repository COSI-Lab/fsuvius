import React from 'react';
import {Link} from 'react-router-dom';
import '../App.css';

class Navbar extends React.Component {
  /*
  constructor() {
    super();
  }
  */
  render() {
    return (
      <div className="navbar">
        <div className="inline_text">Mount Fsuvius</div>
        <Link to="/">
          <div className="inline_button">
              Home
          </div>
        </Link>
        <Link to="/about">
          <div className="inline_button">
              About
          </div>
        </Link>
      </div>
    );
  }
}

  
export default Navbar;