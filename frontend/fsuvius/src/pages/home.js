import React, { Component } from 'react';
import EchoForm from '../components/echoform';
import EntryList from '../components/entrylist';

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
              <p>Home</p>
              <EntryList />
            </div>
          </div>
        </>
    );
  }
}

export default Home;