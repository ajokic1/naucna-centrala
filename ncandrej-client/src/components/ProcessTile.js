import React, { Component } from 'react';

export default class ProcessTile extends Component {
    render() {
        return (
            <div className='link' onClick={() => this.props.handleClick(this.props.pr.key)}>
                {this.props.pr.name}
            </div>
        );
    }
}
