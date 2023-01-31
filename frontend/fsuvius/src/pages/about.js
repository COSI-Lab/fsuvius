import React, { Component } from 'react';
import UserCreator from '../components/usercreator';

class About extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <>
                <div className="spacer"></div>
                <div className="content_outer box">
                    <div className="content_inner">
                        <h1>Mount Fsuvius</h1>
                        <h3>Juno's Edition</h3>
                        <p>Version 0.0.0</p>
                    </div>
                </div>
            </>
        );
    }
}

export default About;