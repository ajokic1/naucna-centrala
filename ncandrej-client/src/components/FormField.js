import React, { Component } from 'react';

export default class FormField extends Component {
    render() {
        let type = 'text';
        if(this.props.field.properties.password) type='password';
        if(this.props.field.type == 'boolean') type='checkbox'; 
        return (
            <div className='form-group'>
                <label htmlFor={this.props.field.name}>{this.props.field.label}</label>
                <input 
                    className='form-control'
                    type={type} 
                    name={this.props.field.name} 
                    id={this.props.field.name} 
                    required={this.props.field.required}
                    value={this.props.value}
                    onChange={this.props.handleChange}/>
            </div>
        );
    }
}
