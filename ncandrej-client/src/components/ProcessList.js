import React, { Component } from 'react';
import ProcessTile from './ProcessTile';

export default class ProcessList extends Component {
    render() {
        let processes = '';
        if(this.props.processes){
            processes = this.props.processes.map(pr => (
                <ProcessTile pr={pr} key={pr.key} handleClick={this.props.handleClick}/>
            ));
        }
        return (
            <div>
                <h5 className='mt-2 mb-1'>Aktivnosti</h5>
                <hr className='mt-0 mb-2 border-light'/>
                {this.props.processes &&
                    processes
                }
            </div>
        );
    }
}
