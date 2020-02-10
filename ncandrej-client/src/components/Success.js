import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';

class Success extends Component {
    constructor(props) {
        super(props);
        this.state={
            downloadUrl: null,
        };
    }

    componentDidMount(){
        const token = this.props.match.params.token;
        window.axioss.get('/papers/paid/' + token, {responseType: 'blob'})
            .then(response => {
                const url = window.URL.createObjectURL(new Blob([response.data]));
                this.setState(downloadUrl);
            });
    }

    render() {
        return (
            <div>
                Payment successful! 
                {this.state.downloadUrl 
                    ? <a href={this.state.downloadUrl}>Download</a>
                    : 'Generating download link...'
                }

            </div>
        );
    }
}

export default withRouter(Success);
