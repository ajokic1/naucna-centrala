import React, { Component } from 'react';

export default class ProcessTitleBar extends Component {
    render() {
        return (
            <div className='bg-gray p-4'>
                <h2>{this.props.processName}</h2>
                <small>
                    ID procesa: {this.props.processId} 
                    <span className='link p-1 mx-2' onClick={this.props.deleteProcess}>Obriši proces</span>
                </small>
                
            </div>
        );
    }
}
