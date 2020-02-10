import React, { Component } from 'react';

export default class PaperView extends Component {
    constructor(props) {
        super(props);
        this.paperStatus= {
            MUST_PAY: 'Jednokratno plaćanje',
            PAID: 'Plaćen',
            SUBSCRIBED: 'Aktivna pretplata',
            OPEN_ACCESS: 'Besplatan',
        }
        this.handleDownloadClick = this.handleDownloadClick.bind(this);
    }
    handleDownloadClick() {
        window.axioss.get(this.props.paper.redirectUrl)
            .then(json => {
                if(this.props.paper.status=='MUST_PAY'){
                    window.location.href=json.data;    
                }
                
            });
    }
    render() {
        return (
            <div>
                <h1>{this.props.paper.title}</h1>
                <p>
                    <strong>Pristup radu: </strong>{this.paperStatus[this.props.paper.status]}
                    <button className='btn btn-primary' onClick={this.handleDownloadClick}>Preuzmi</button>
                </p>
                <p><strong>Autor: </strong>{this.props.paper.authorName}</p>
                <p><strong>DOI: </strong>{this.props.paper.doi}</p>
                <p><strong>Keywords: </strong>{this.props.paper.keywords}</p>
                <p><strong>Abstract: </strong>{this.props.paper.pabstract}</p>
            </div>
        );
    }
}
