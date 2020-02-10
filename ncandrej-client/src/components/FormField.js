import React, { Component } from 'react';
import Select from 'react-select';

export default class FormField extends Component {
    constructor(props){
        super(props);
        this.state = {
            values: [],
        }
        this.handleSelect = this.handleSelect.bind(this);
    }
    componentDidMount() {
        // if(this.props.field.properties.valuesUrl){
        //     window.axioss.get(this.props.field.properties.valuesUrl)
        //         .then(json => {
        //             this.setState({values: json.data});
        //         });
        // }
        if(this.props.field.properties.redirectUrl){
            this.setState({redirecting: this.props.field.name})
            this.props.handleSubmit(this.props.field.value);
        }
    } 
    handleSelect(selectedOption, target) {
        let field = this.props.field;
        field.value.selectedValue = selectedOption.value;
        this.props.handleSelect(target.name, field.value);
    }
    render() {
        let type = 'text';
        if(this.props.field.properties.password) type='password';
        if(this.props.field.type == 'boolean') type='checkbox'; 
        
        let inputField;
        let options;
        if(this.props.field.type=='List'){
            options = Object.keys(this.props.field.value.options).map(key => ({
                value: key, label: this.props.field.value.options[key]
            }));
            inputField = (<Select
                name={this.props.field.name}
                options={options} 
                onChange={this.handleSelect}/>);
        } else if (this.props.field.properties.redirectUrl) {
            inputField=(<p>Redirekcija...</p>);
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
