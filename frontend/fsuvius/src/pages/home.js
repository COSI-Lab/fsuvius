import React, { Component } from 'react';
import UserList from '../components/userlist';
import UserCreator from '../components/usercreator';
import FsuviusLogo from '../fsuvius.png';
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
                <img src={FsuviusLogo} alt="Mount Fsuvius" width="100%"></img>
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