import React, { Component } from 'react';
import JournalItem from './JournalItem';

export default class JournalList extends Component {
    render() {
        let journals = [];
        if(this.props.journals) {
            journals = this.props.journals.map(journal => 
                <JournalItem key={journal.id} journal={journal} handleClick={this.props.handleClick}/>)
        }
        return (
            <div>
                <h1>Časopisi</h1>
                {journals}
            </div>
        );
    }
}
