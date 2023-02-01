import React, {Component} from 'react';
import FsuviusMap from '../FsuviusMap.js';
import UserEditor from './usereditor.js';

class UserList extends Component {
    constructor() {
        super();
        this.state = {users: []}
    }

    async componentDidMount() {
        const response = await fetch(FsuviusMap.API_URL + "/users");
        const body = await response.json();
        this.setState({users: body});
    }

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
    /*
    render() {
        const {users} = this.state;
        return (
            <div>
                <table>
                    <tr>
                        <th><b>ID</b></th>
                        <th><b>Name</b></th>
                        <th><b>Balance</b></th>
                        <th><b>URL</b></th>
                    </tr>
                    {users.map(user =>
                        <tr key={user.id}>
                            <th>{user.id}</th>
                            <th>{user.name}</th>
                            <th>{user.balance}</th>
                            <th>{user.url}</th>
                        </tr>
                    )}
                </table>
            </div>
        )
    }
    */
}
export default UserList;