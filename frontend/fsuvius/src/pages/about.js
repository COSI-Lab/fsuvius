import React, { Component } from 'react';
import FsuviusLogo from '../fsuvius.png';

class About extends Component {
    constructor() {
        super();
    }
    render() {
        return (
            <>
                <div className="spacer"></div>
                <div className="content_outer box">
                    <div className="content_inner">
                        <img src={FsuviusLogo} alt="Mount Fsuvius" width="100%"></img>
                        <p><a href="https://github.com/lavajuno/fsuvius">https://github.com/lavajuno/fsuvius</a></p>
                        <p>Version 0.0.0</p>
                    </div>
                </div>
            </>
        );
    }
}

export default About;