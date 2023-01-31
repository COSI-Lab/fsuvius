import React, { Component } from 'react';
import EntryList from '../components/userlist';

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
              <h1>Home</h1>
              <EntryList />
            </div>
          </div>
        </>
    );
  }
}

export default Home;