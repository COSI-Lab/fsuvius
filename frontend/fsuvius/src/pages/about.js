import React, { Component } from 'react';
import FsuviusLogo from './fsuvius.png';
import ElectricBoogaloo from './electricboogaloo.gif'

class About extends Component {
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
                        <img src={FsuviusLogo} alt="Mount Fsuvius 2" width="100%"></img>
                        <div className="centered_container">
                            <img src={ElectricBoogaloo} alt="Electric Boogaloo" width="100%"></img>
                        </div>
                        
                        <p><a href="https://github.com/lavajuno/fsuvius">https://github.com/lavajuno/fsuvius</a></p>
                        <p>Version 0.0.0</p>
                    </div>
                </div>
            </>
        );
    }
}

export default About;