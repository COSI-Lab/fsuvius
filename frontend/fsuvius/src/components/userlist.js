import React, {Component} from 'react';
import FsuviusMap from '../FsuviusMap.js';
import UserEditor from './usereditor.js';

/* UserList handles displaying a list of all stored users */
class UserList extends Component {
    constructor() {
        super();
        this.state = {users: []}
    }

    /* Fetch and display users upon mount */
    async componentDidMount() {
        const response = await fetch(FsuviusMap.API_URL + "/users");
        const body = await response.json();
        this.setState({users: body});
    }

    /* Display */
    render() {
        const {users} = this.state;
        return (
            <>
                {users.map(user =>
                    <div key={user.id}>
                        <UserEditor
                            newID = {user.id}
                            newName = {user.name}
                            newBalance = {user.balance}
                        />
                    </div>
                )}
            </>
        )
    }
}

export default UserList;