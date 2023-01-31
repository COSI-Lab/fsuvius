import React from "react";
import FsuviusMap from "../FsuviusMap";

class UserCreator extends React.Component {
  constructor(props) {
    super(props);
    this.state = {value: ''};
    this.handleCreate = this.handleCreate.bind(this);
  }

  async handleCreate(event) {
    event.preventDefault();
    await fetch(FsuviusMap.API_URL + "/users", {
        method: 'POST',
        headers: {
            'Accept':'application/json',
            'Content-Type':'application/json',
        },
        body: "New User",
    });
    window.location.reload();
  }

  render() {
    return (
      <>
        <input type="button" value="Create User" onClick={this.handleCreate}/>
      </>
    );
  }
}

export default UserCreator;