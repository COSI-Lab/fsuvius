import React, { Component } from 'react';
import FsuviusMap from '../FsuviusMap.js';

/* 
  UserEditor is a single editable entry in the list of users. 
*/
class UserEditor extends Component {
  constructor({newID, newName, newBalance}) {
    super();
    
    /* Initialize state */
    this.state={id: newID, name: newName, balance: newBalance}

    /* Bind input handlers */
    this.handleNameChange = this.handleNameChange.bind(this);
    this.handleBalanceChange = this.handleBalanceChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleRemove = this.handleRemove.bind(this);
    this.handlePlus = this.handlePlus.bind(this);
    this.handleMinus = this.handleMinus.bind(this);
  }

  /* Update state when user changes name */
  handleNameChange(event) {
    this.setState({name: event.target.value});
  }

  /* Update state when user changes balance */
  handleBalanceChange(event) {
    this.setState({balance: event.target.value});
  }

  /* Update this user's account with this UserEditor's current state */
  async handleSubmit(event) {
    event.preventDefault();
    await fetch(FsuviusMap.API_URL + "/users/" + this.state.id, {
        method: 'PUT',
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json',
        },
        body: JSON.stringify(this.state),
    });
    window.location.reload();
  }

  /* Remove this user's account */
  async handleRemove(event) {
    event.preventDefault();
    if(window.confirm("Are you sure you want to delete this user?")) {
      await fetch(FsuviusMap.API_URL + `/users/${this.state.id}`, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
      });
      window.location.reload();
    }
  }

  /* Add 1 FSU to this user's account */
  async handlePlus(event) {
    const incrementedBalance = this.state.balance + 1
    this.setState({balance: incrementedBalance});
    event.preventDefault();
    await fetch(FsuviusMap.API_URL + "/users/" + this.state.id, {
        method: 'PUT',
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json',
        },
        body: JSON.stringify({id: this.state.id, name: this.state.name, balance: this.state.balance + 1}),
    });
  }

  /* Remove 1 FSU from this user's account */
  async handleMinus(event) {
    const incrementedBalance = this.state.balance - 1
    this.setState({balance: incrementedBalance});
    event.preventDefault();
    await fetch(FsuviusMap.API_URL + "/users/" + this.state.id, {
        method: 'PUT',
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json',
        },
        body: JSON.stringify({id: this.state.id, name: this.state.name, balance: this.state.balance - 1}),
    });
  }

  /* Display */
  render() {
    return (
        <>
        <div>
        <form onSubmit={this.handleSubmit}>
            <label>
                <input type="text" value={this.state.name} onChange={this.handleNameChange} />
                <input type="text" value={this.state.balance} onChange={this.handleBalanceChange} />
            </label>
            <button onClick={this.handlePlus}>+1</button>
            <button onClick={this.handleMinus}>-1</button>
            <button type="submit">Save</button>
            <button onClick={this.handleRemove}>Remove</button>
        </form>
        </div>
        </>
    );
  }
}

export default UserEditor;
