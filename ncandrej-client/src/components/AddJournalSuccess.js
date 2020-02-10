import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';

class AddJournalSuccess extends Component {

    constructor(props) {
        super(props);
        this.state = {
            completed: false,
        }
    }

    componentDidMount() {
        window.axioss.post('/process/message/' + this.props.match.params.processId, 'payment_methods_added')
            .then(json => {
                if(json.data === true) {
                    this.setState({completed: true});
                }
            });
    }
    
    render() {
        const completedMessage = (
            <div>
                <h1>Novi časopis je uspješno kreiran.</h1>
                <p>Prije aktivacije, časopis mora biti odobren od strane administratora.</p>
            </div>
        );
        return (
            <div>
            {this.state.completed
                ? completedMessage
                : <p>Loading...</p>
            }
            </div>
        );
    }
}
export default withRouter(AddJournalSuccess);