import React, {Component} from 'react';
import FsuviusMap from '../FsuviusMap.js';

class EntryList extends Component {
    constructor(props) {
        super(props);
        this.state = {entries: []}
    }

    async componentDidMount() {
        const response = await fetch(FsuviusMap.API_URL + "/entries");
        const body = await response.json();
        this.setState({entries: body});
    }

    render() {
        const {entries} = this.state;
        return (
            <div>
                <p>Entries:</p>
                {entries.map(entry =>
                    <div key={entry.id}>
                        {entry.name} ({entry.balance})
                    </div>
                )}
            </div>
        )
    }
}
export default EntryList;