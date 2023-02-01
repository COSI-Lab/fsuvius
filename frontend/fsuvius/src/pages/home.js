import React, { Component } from 'react';
import UserList from '../components/userlist';
import UserCreator from '../components/usercreator';
import FsuviusLogo from './fsuvius.png';
import FsuviusMap from '../FsuviusMap.js';

class Home extends Component {
  /*
  constructor() {
    super();
  }
  */
  render() {
    return (
        <>
          <div className="spacer"></div>
            <div className="content_outer box">
              <div className="content_inner">
                <div className="marquee_container">
                  <div className="marquee">
                    <b>Clarkson Open Source Institute - Mount Fsuvius - Floating Soda Unit Exchange</b>
                  </div>
                </div>
                <div className="centered_container">
                  <img src={FsuviusLogo} alt="Mount Fsuvius" width="100%"></img>
                </div>
                <h3>1 FSU = {FsuviusMap.EXCHANGE_RATE} USD</h3>
                <UserList />
                <UserCreator />
            </div>
          </div>
        </>
    );
  }
}

export default Home;