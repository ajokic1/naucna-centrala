import React, { Component } from 'react';
import TaskTile from './TaskTile';

export default class TaskList extends Component {
    render() {
        let tasks = '';
        if(this.props.tasks){
            tasks = this.props.tasks.map(task => (
                <TaskTile task={task} key={task.id} handleClick={this.props.handleClick}/>
            ));
        }
        return (
            <div>
                <h5 className='mt-2 mb-1'>Zadaci</h5>
                <hr className='mt-0 mb-2'/>
                {this.props.tasks &&
                    tasks
                }
            </div>
        );
    }
}