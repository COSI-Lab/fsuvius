import React from "react";

class EchoForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {value: ''};
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {    this.setState({value: event.target.value});  }
  handleSubmit(event) {
    alert('You submitted: ' + this.state.value);
    event.preventDefault();
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <label>
          Echo:
          <input type="text" value={this.state.value} onChange={this.handleChange} />        
        </label>
        <div class="inline_button">
        <input type="submit" value="Submit"/>
        </div>
      </form>
    );
  }
}

export default EchoForm;