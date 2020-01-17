import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import axioss from './axioss';
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import Home from './components/Home';
import Register from './components/Register';
import Login from './components/Login';
import Navbar from './components/Navbar';

export default class App extends Component {
    constructor(props){
        super(props);
        this.state={
            user:null,
        }
        this.authSuccess = this.authSuccess.bind(this);
        this.logout = this.logout.bind(this);
    }
    componentDidMount() {
        let user = localStorage["user"];
        if (user) {
            user=JSON.parse(user);
            this.setState({user: user });
            axioss.defaults.headers.common['Authorization'] = 
                'Bearer ' + user.auth_token;
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
            axioss.defaults.headers.common['Authorization'] = 
                'Bearer ' + user.token;
        } else {
            this.setState({errorMessage: 'Došlo je do greške'});
        }
    }
    render() {
        return (
            <div style={{paddingTop: '3.5rem'}}>
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
                            <Login authSuccess={this.authSuccess}/>
                        </Route>
                    </Switch>
                </Router>
            </div>
        );
    }
}
