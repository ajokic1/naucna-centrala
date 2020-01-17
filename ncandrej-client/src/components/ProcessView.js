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
    }
    loadTask() {
        this.props.loadTaskFalse();
        window.axioss.get('/process/tasks/'+this.props.taskId)
            .then(json=>{
                this.setState({task: json.data});
                json.data.fields.map(field => this.setState({[field.name]: field.type=='boolean'?false:''}));
            });
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
    render() {
        if(this.props.loadTask) this.loadTask();
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
            <div>
                {this.state.task && fields}
                {this.state.task && <div className='btn btn-primary' onClick={this.handleSubmit}>Submit</div>}
            </div>
        );
    }
}
