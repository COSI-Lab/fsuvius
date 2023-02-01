import React from "react";
import FsuviusMap from "../FsuviusMap";

/* UserCreator handles creating new users */
class UserCreator extends React.Component {
  constructor() {
    super();
    /* Bind input handlers */
    this.handleCreate = this.handleCreate.bind(this);
  }

  /* Create a new user */
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

  /* Display */
  render() {
    return (
      <>
        <button onClick={this.handleCreate}>Create User</button>
      </>
    );
  }
}

export default UserCreator;