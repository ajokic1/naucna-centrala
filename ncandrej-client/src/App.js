import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import Home from './components/Home';
import Register from './components/Register';
import Login from './components/Login';
import Navbar from './components/Navbar';
import Dashboard from './components/Dashboard';

export default class App extends Component {
    constructor(props){
        super(props);
        this.state={
            user:null,
            requestedPath: '/',
        }
        this.authSuccess = this.authSuccess.bind(this);
        this.logout = this.logout.bind(this);
        this.setRequestedPath = this.setRequestedPath.bind(this);
    }
    componentDidMount() {
        let user = localStorage["user"];
        if (user) {
            user=JSON.parse(user);
            this.authSuccess(true, user);
        }
    }
    logout() {
        this.setState({user: null});
        localStorage["user"] = '';
    }
    authSuccess(isSuccess, user){
        if(isSuccess){
            this.setState({user: user});
            localStorage["user"] = JSON.stringify(user);
            console.log('authsuccess');
            window.axioss.defaults.headers.common['Authorization'] = 
                'Bearer ' + user.token;
        } else {
            this.setState({errorMessage: 'Došlo je do greške'});
        }
    }
    setRequestedPath(requestedPath) {
        this.setState({requestedPath});
    }
    render() {
        return (
            <div className='h-100' style={{paddingTop: '3.5rem'}}>
                <Router>
                    <Navbar user={this.state.user} logout={this.logout}/>
                    <Switch>                        
                        <Route exact path="/">
                            <Home/>
                        </Route>
                        <Route path='/register'>
                            <Register/>
                        </Route>
                        <Route path='/login'>
                            <Login requestedPath={this.requestedPath} authSuccess={this.authSuccess}/>
                        </Route>
                        {this.state.user &&
                            <Route>
                                <Dashboard user={this.state.user} setRequestedPath={this.setRequestedPath}/>
                            </Route>}
                    </Switch>
                </Router>
            </div>
        );
    }
}
