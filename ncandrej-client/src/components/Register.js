import React, { Component } from 'react';
import FormField from './FormField';
import { Redirect } from 'react-router-dom';
 
export default class Register extends Component {
    constructor(props) {
        super(props)
        this.state = {
            processId: '',
            task: null,
            isComplete: false,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.getTask = this.getTask.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
    }
    handleChange(event) {
        if(event.target.type == 'checkbox')
            this.setState({[event.target.name]: event.target.checked});
        else this.setState({[event.target.name]: event.target.value});
    }
    handleSubmit() {
        let fields = [];
        this.state.task.fields.map(field => fields.push({name: field.name, value: this.state[field.name]}));
        window.axioss.post('/user/register/' + this.state.task.taskId, fields)
            .then(() => {this.setState({task: null})});
    }
    componentDidMount() {
        window.axioss.get('/user/register')
            .then(json => {
                this.setState({processId: json.data});
            });
        this.getTaskTimer = setInterval(this.getTask, 1000);
    }
    componentWillUnmount() {
        clearInterval(this.getTaskTimer);
    }
    handleSelect(selectedOption, target) {
        this.setState({[target.name]: selectedOption.value});
    }
    getTask() {
        if(!this.state.task){
            window.axioss.get('/user/register/process/' + this.state.processId)
            .then(json =>{
                if(json.data.taskId == 'process_ended')
                    this.setState({isComplete: true});
                else{
                    this.setState({task: json.data});
                    json.data.fields.map(field => this.setState({[field.name]: field.type=='boolean'?false:''}));    
                }
                
            });    
        }        
    }
    render() {
        if(this.state.isComplete)
            return <Redirect to='/'/>
        let fields;
        if(this.state.task){
            fields = this.state.task.fields.map(field => 
                <FormField 
                    field={field} 
                    key={field.name} 
                    value={this.state[field.name]} 
                    handleChange={this.handleChange}
                    handleSelect={this.handleSelect}/>
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
