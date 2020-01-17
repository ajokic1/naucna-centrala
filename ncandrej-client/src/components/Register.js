import React, { Component } from 'react';
import axioss from '../axioss';
import FormField from './FormField';

export default class Register extends Component {
    constructor(props) {
        super(props)
        this.state = {
            processId: '',
            task: null
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.getTask = this.getTask.bind(this);
    }
    handleChange(event) {
        this.setState({[event.target.name]: event.target.value});
    }
    handleSubmit() {
        let fields = [];
        this.state.task.fields.map(field => fields.push({name: field.name, value: this.state[field.name]}));
        axioss.post('/user/register/' + this.state.task.taskId, fields)
            .then(() => {this.setState({task: null})});
    }
    componentDidMount() {
        axioss.get('/user/register')
            .then(json => {
                this.setState({processId: json.data});
            });
        this.getTaskTimer = setInterval(this.getTask, 1000);
    }
    componentWillUnmount() {
        clearInterval(this.getTaskTimer);
    }
    getTask() {
        console.log("tick");
        if(!this.state.task){
            console.log("gotem");
            axioss.get('/user/register/process/' + this.state.processId)
            .then(json =>{
                this.setState({task: json.data});
                json.data.fields.map(field => this.setState({[field.name]: ''}));
            });    
        }        
    }
    render() {
        let fields;
        if(this.state.task){
            fields = this.state.task.fields.map(field => 
                <FormField 
                    field={field} 
                    key={field.name} 
                    value={this.state[field.name]} 
                    handleChange={this.handleChange}/>
            );    
        }
        
        return (
            <div className='container'>
                <h1 className='mb-3'>Registracija novog korisnika</h1>
                <div>
                    {this.state.task && fields}
                </div>
                <div className='btn btn-primary' onClick={this.handleSubmit}>Submit</div>
            </div>
        );
    }
}
