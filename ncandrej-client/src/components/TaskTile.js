import React, { Component } from 'react';

export default class TaskTile extends Component {
    render() {
        return (
            <div className='link' onClick={() => this.props.handleClick(this.props.task)}>
                {this.props.task.name}
            </div>
        );
    }
}
