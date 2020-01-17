import React, { Component } from 'react';
import { Link } from 'react-router-dom';

export default class Home extends Component {
    render() {
        return (
            <div>
                <Link to='/register'><div className='btn btn-primary'>Registracija</div></Link>
            </div>
        );
    }
}
