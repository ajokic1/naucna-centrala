import React, { Component } from 'react';
import Sidebar from './Sidebar';
import ProcessList from './ProcessList';
import TaskList from './TaskList';
import ProcessView from './ProcessView';

export default class Dashboard extends Component {
    constructor(props) {
        super(props);
        this.state={
            processes: null,
            loadTask: false,
        }
        this.handleProcessClick = this.handleProcessClick.bind(this);
        this.handleTaskClick = this.handleTaskClick.bind(this);
        this.loadTaskFalse = this.loadTaskFalse.bind(this);
        this.loadTasks = this.loadTasks.bind(this);
    }
    componentDidMount() {
        window.axioss.get('/process/definitions')
            .then(json => {
                this.setState({processes: json.data});
            });
        this.loadTasks();
        this.getTaskTimer = setInterval(this.loadTasks, 3000);
    }
    componentWillUnmount() {
        clearInterval(this.getTaskTimer);
    }
    loadTasks() {
        window.axioss.get('/process/tasks/user/'+this.props.user.username)
            .then(json => {
                this.setState({tasks: json.data});
            });
    }
    handleProcessClick(key) {
        window.axioss.get('/process/start/'+key+'/user/'+this.props.user.username)
            .then(json => {
                this.setState({processId: json.data});
                window.axioss.get('/process/tasks/user/'+this.props.user.username)
                    .then(json => {
                        this.setState({tasks: json.data});
                    });
            });
    }
    handleTaskClick(id) {
        this.setState({taskId: id, loadTask: true});
    }
    loadTaskFalse() {
        this.setState({loadTask: false});
    }
    render() {
        return (
            <div className='row h-100 w-100 mx-0'>
                <div className='h-100 overflow-auto col-lg-3 col-md-4 col-sm-5  bg-light border border-bottom-0 border-top-0 border-left-0'>
                    <Sidebar>
                        <div className='btn btn-primary' onClick={this.loadTasks}>Refresh</div>
                        <ProcessList processes={this.state.processes} handleClick={this.handleProcessClick}/>
                        {this.state.tasks &&
                            <TaskList tasks={this.state.tasks} handleClick={this.handleTaskClick}/>
                        }
                        
                    </Sidebar>
                </div>
                <div className='overflow-auto col-lg-9 col-md-8 col-sm-7 bg-white h-100 p-5'>
                    <ProcessView 
                        taskId={this.state.taskId} 
                        loadTask={this.state.loadTask}
                        loadTaskFalse={this.loadTaskFalse}/>
                </div>
            </div>
        );
    }
}
