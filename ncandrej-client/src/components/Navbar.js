import React, { Component } from 'react';
import {Link} from 'react-router-dom';
import AuthControls from './AuthControls';

export default class Navbar extends Component {
    render() {
        return (
            <nav className="navbar fixed-top navbar-expand-md navbar-dark bg-dark">
                <a className="navbar-brand" href="#">Naucna centrala</a>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav mr-auto">
                        <li className="nav-item active">
                            <Link to='/'><div className="nav-link" href="#">Početna</div></Link>
                        </li>
                        
                    </ul>
                    <AuthControls 
                        user={this.props.user} 
                        logout={this.props.logout}/>
                </div>
            </nav>
        );
    }
}
