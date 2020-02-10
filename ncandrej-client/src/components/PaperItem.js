import React, { Component } from 'react';

export default class PaperItem extends Component {
    render() {
        return (
            <div className='my-3'>
                <div className='link'  onClick={() => this.props.handleClick(this.props.paper)}><strong>{this.props.paper.title}</strong></div>
                <div>Autor: {this.props.paper.authorName}; DOI: {this.props.paper.doi}</div>
            </div>
        );
    }
}
