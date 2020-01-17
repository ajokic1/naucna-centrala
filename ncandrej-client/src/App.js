import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import axioss from './axioss';
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import Home from './components/Home';
import Register from './components/Register';

export default class App extends Component {
    constructor(props){
        super(props);
        this.state={
            user:{},
            isLoggedIn: false,
        }
    }
    componentDidMount() {
        let user = localStorage["user"];
        if (user) {
            user=JSON.parse(user);
            this.setState({ isLoggedIn: true, user: user });
            axioss.defaults.headers.common['Authorization'] = 
                'Bearer ' + user.auth_token;
        }
    }
    render() {
        return (
            <div>
                <Router>
                    <Switch>
                        <Route exact path="/">
                            <Home/>
                        </Route>
                        <Route path='/register'>
                            <Register/>
                        </Route>
                    </Switch>
                </Router>
            </div>
        );
    }
}
