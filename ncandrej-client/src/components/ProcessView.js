import React, { Component } from 'react';
import FormField from './FormField';

export default class ProcessView extends Component {
    constructor(props) {
        super(props);
        this.state={

        };
        this.loadTask = this.loadTask.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
        this.loadNextTask = this.loadNextTask.bind(this);
    }
    loadTask() {
        this.props.loadTaskFalse();
        window.axioss.get('/process/tasks/'+this.props.taskId)
            .then(json=>{
                this.setState({task: json.data});
                json.data.fields.map(field => this.setState({[field.name]: field.type=='boolean'?false:''}));
            });
    }
    loadNextTask() {
        this.props.loadTaskFalse();
        window.axioss.get('/process/tasks/next/user/' + this.props.user.username + '/process/' + this.props.processId)
            .then(json => {
                this.setState({task: json.data});
                json.data.fields.map(field => this.setState({[field.name]: field.type=='boolean'?false:''}));
            })
    }
    handleChange(event) {
        if(event.target.type == 'checkbox')
            this.setState({[event.target.name]: event.target.checked});
        else this.setState({[event.target.name]: event.target.value});
    }
    handleSubmit(redirectUrl) {
        let fields = [];
        this.state.task.fields.map(field => fields.push({name: field.name, value: this.state[field.name], type: field.type}));
        window.axioss.post('/process/tasks/' + this.state.task.taskId, fields)
            .then(() => {
                this.setState({task: null})
                if(redirectUrl) {
                    window.location.href = redirectUrl;
                }
                this.loadNextTask();
                this.props.refreshTaskList();
            });
    }
    handleSelect(name, value) {
        this.setState({[name]: value});
    }
    render() {
        if(this.props.loadTask) this.loadTask();
        let fields;
        if(this.state.task && this.state.task.fields){
            fields = this.state.task.fields.map(field => 
                <FormField 
                    field={field} 
                    key={field.name} 
                    value={this.state[field.name]} 
                    handleChange={this.handleChange}
                    handleSelect={this.handleSelect}
                    handleSubmit={this.handleSubmit}/>
            );    
        }
        return (
            <div className='m-3'>
                {this.state.task &&
                <div>
                    <h3>{this.state.task.taskName}</h3>
                    {this.state.task.description && <p>{this.state.task.description}</p>}
                    <hr/>
                    {fields}
                    <div className='btn btn-primary' onClick={()=>this.handleSubmit()}>Submit</div>
                </div>}
            </div>
        );
    }
}
