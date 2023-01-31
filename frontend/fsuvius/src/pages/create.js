import React, { Component } from 'react';
import UserForm from '../components/userform';

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
                <UserForm />
                </div>
            </div>
            </>
        );
    }
}

export default Create;