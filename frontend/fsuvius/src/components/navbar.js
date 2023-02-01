import React from 'react';
import {Link} from 'react-router-dom';
import '../App.css';

/*
  Navbar is fixed at the top of the 
  screen and handles navigation between pages. 
*/
class Navbar extends React.Component {
  /*
  constructor() {
    super();
  }
  */

  /* Display */
  render() {
    return (
      <div className="navbar">
        <div className="inline_text"><b>Mount Fsuvius</b></div>
        <Link to="/">
          <div className="inline_button">Home</div>
        </Link>
        <Link to="/about">
          <div className="inline_button">About</div>
        </Link>
      </div>
    );
  }
}

export default Navbar;