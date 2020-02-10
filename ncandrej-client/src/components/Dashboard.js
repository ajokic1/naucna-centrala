import React, { Component } from 'react';
import Sidebar from './Sidebar';
import ProcessList from './ProcessList';
import TaskList from './TaskList';
import ProcessView from './ProcessView';
import ProcessTitleBar from './ProcessTitleBar';

export default class Dashboard extends Component {
    constructor(props) {
        super(props);
        this.state={
            processes: null,
            loadTask: false,
            processId: null,
            processName: null,
        }
        this.handleProcessClick = this.handleProcessClick.bind(this);
        this.handleTaskClick = this.handleTaskClick.bind(this);
        this.loadTaskFalse = this.loadTaskFalse.bind(this);
        this.loadTasks = this.loadTasks.bind(this);
        this.loadDefinitions = this.loadDefinitions.bind(this);
        this.handleRefresh = this.handleRefresh.bind(this);
        this.handleProcessDelete = this.handleProcessDelete.bind(this);
    }
    componentDidMount() {
        this.loadDefinitions();
        this.loadTasks();
        //this.getTaskTimer = setInterval(this.loadTasks, 3000);
    }
    componentWillUnmount() {
        //clearInterval(this.getTaskTimer);
    }
    handleRefresh() {
        this.loadTasks();
        this.loadDefinitions();
    }
    loadDefinitions() {
        window.axioss.get('/process/definitions')
            .then(json => {
                this.setState({processes: json.data});
            });
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
    handleTaskClick(task) {
        this.setState({taskId: task.id, processId: task.processId, processName: task.processName, loadTask: true});
    }
    loadTaskFalse() {
        this.setState({loadTask: false});
    }
    handleProcessDelete() {
        window.axioss.delete('/process/' + this.state.processId)
            .then(json => {
                this.setState({taskId: null, processId: null, processName: null});
                this.loadTasks();
            });
        
    }
    render() {
        return (
            <div className='row h-100 w-100 mx-0'>
                <div className='h-100 overflow-auto col-lg-3 col-md-4 col-sm-5  bg-secondary text-white'>
                    <Sidebar>
                        <div className='w-100 text-center link' onClick={this.handleRefresh}>Refresh</div>
                        <ProcessList processes={this.state.processes} handleClick={this.handleProcessClick}/>
                        {this.state.tasks &&
                            <TaskList tasks={this.state.tasks} handleClick={this.handleTaskClick}/>
                        }
                        
                    </Sidebar>
                </div>
                <div className='col-lg-9 col-md-8 col-sm-7 bg-white h-100 p-0'>
                    {this.state.processName &&
                        <ProcessTitleBar 
                        processId={this.state.processId} 
                        processName={this.state.processName}
                        deleteProcess={this.handleProcessDelete}/>}
                    <div className='overflow-auto h-100'>
                        {this.state.processId && <ProcessView 
                            taskId={this.state.taskId}
                            processId={this.state.processId} 
                            loadTask={this.state.loadTask}
                            loadTaskFalse={this.loadTaskFalse}
                            user={this.props.user}
                            refreshTaskList={this.loadTasks}/>}
                    </div>
                </div>
            </div>
        );
    }
}
