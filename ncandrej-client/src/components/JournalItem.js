import React, { Component } from 'react';

export default class JournalItem extends Component {
    render() {
        return (
            <div className='my-3'>
                <div className='link' onClick={() => this.props.handleClick(this.props.journal)}><strong>{this.props.journal.name}</strong> {this.props.journal.openAccess && "[Open access]"}</div>
                <div>Urednik: {this.props.journal.editor}; ISSN: {this.props.journal.issn}</div>
            </div>
        );
    }
}
