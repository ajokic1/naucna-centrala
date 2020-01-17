import React, { Component } from 'react';
import { Link } from 'react-router-dom';

export default class AuthControls extends Component {
    render() {
        return (
            <div>
            {!this.props.user &&
                <div>
                    <Link className='btn btn-outline-light mx-2' to='/login'>Login</Link>
                    <Link className='btn btn-outline-light mx-2' to='/register'>Register</Link>
                </div>
            }
            {this.props.user && 
                <div className='text-light'>
                <strong>{this.props.user.firstName + ' ' + this.props.user.lastName}</strong>
                <button className='btn btn-outline-light mx-2' onClick={this.props.logout}>Log out</button></div>
            }
            </div>
        );
    }
}
