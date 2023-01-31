import React, { Component } from 'react';
import UserList from '../components/userlist';
import UserCreator from '../components/usercreator';

class Home extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
        <>
          <div className="spacer"></div>
            <div className="content_outer box">
              <div className="content_inner">
              <h3>1 FSU = 0.75 USD</h3>
              <UserList />
              <UserCreator />
            </div>
          </div>
        </>
    );
  }
}

export default Home;