import React, { Component } from 'react';
import Select from 'react-select';

export default class FormField extends Component {
    constructor(props){
        super(props);
        this.state = {
            values: [],
        }
    }
    componentDidMount() {
        if(this.props.field.properties.valuesUrl){
            window.axioss.get(this.props.field.properties.valuesUrl)
                .then(json => {
                    this.setState({values: json.data});
                });
        }
    }
    render() {
        let type = 'text';
        if(this.props.field.properties.password) type='password';
        if(this.props.field.type == 'boolean') type='checkbox'; 
        
        let inputField;
        if(this.props.field.properties.valuesUrl){
            inputField = (<Select
                name={this.props.field.name}
                options={this.state.values} 
                onChange={this.props.handleSelect}/>);
        } else inputField=(
            <input className='form-control'
                type={type} 
                name={this.props.field.name} 
                id={this.props.field.name} 
                required={this.props.field.required}
                value={this.props.value}
                checked={this.props.value}
                onChange={this.props.handleChange}/>);

        return (
            <div className='form-group'>
                <label htmlFor={this.props.field.name}>{this.props.field.label}</label>
                {inputField}
            </div>
        );
    }
}
