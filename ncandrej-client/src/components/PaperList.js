import React, { Component } from 'react';
import PaperItem from './PaperItem';

export default class PaperList extends Component {
    render() {
        let papers = [];
        if(this.props.papers) {
            papers = this.props.papers.map(paper => 
                <PaperItem key={paper.id} paper={paper} handleClick={this.props.handleClick}/>)
        }
        return (
            <div>
                {papers}
            </div>
        );
    }
}
