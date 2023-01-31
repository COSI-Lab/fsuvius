import React, { Component } from 'react';
import EchoForm from '../components/echoform';

class Create extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <>
            <div className="spacer"></div>
                <div className="content_outer box">
                <div className="content_inner">
                <p>Create Account</p>
                <EchoForm />
                </div>
            </div>
            </>
        );
    }
}

export default Create;