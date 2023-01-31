import React, { Component } from 'react';
import FsuviusMap from '../FsuviusMap.js';

class UserEditor extends Component {
    constructor({newID, newName, newBalance}) {
        super();
        this.state={id: newID, name: newName, balance: newBalance}
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleBalanceChange = this.handleBalanceChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleRemove = this.handleRemove.bind(this);
      }

      handleNameChange(event) {
        this.setState({name: event.target.value});
      }

      handleBalanceChange(event) {
        this.setState({balance: event.target.value});
      }

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

      render() {
        return (
            <>
            <div>
            <form onSubmit={this.handleSubmit}>
                <label>
                    <input type="text" value={this.state.name} onChange={this.handleNameChange} />
                    <input type="text" value={this.state.balance} onChange={this.handleBalanceChange} />
                </label>
                <input type="submit" value="Edit"/>
                <input type="button" value="Remove" onClick={this.handleRemove}/>
            </form>
            </div>
            </>
        );
      }
}
export default UserEditor;