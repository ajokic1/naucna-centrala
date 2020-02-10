import React, { Component } from 'react';
import { Route, withRouter } from 'react-router-dom';
import PaperList from './PaperList';
import PaperView from './PaperView';

class JournalView extends Component {
    componentDidMount() {
        if(this.props.journal){
            if(!this.props.journal.openAccess){
            window.axioss
                .get('/journals/' + this.props.journal.id + '/subscribe/check')
                .then(json => {this.props.setSubscribed(json.data)});    
            }
        } else {
            window.axioss
                .get('/journals/' + this.props.match.params.journalId)
                .then(json => {
                    this.props.handleJournalClick(json.data);
                });
        }
        
    }
    render() {
        if(!this.props.journal) return (<p>Loading...</p>);
        return (
            <div>
                <h1>{this.props.journal.name}</h1>
                <hr/>
                {this.props.journal.subscribed && <p>Pretplaćeni ste na ovaj časopis.</p>}
                {this.props.journal.openAccess && <p>Open access časopis</p>}
                <p><strong>Urednik: </strong>{this.props.journal.editor}</p>
                <p><strong>ISSN: </strong>{this.props.journal.issn}</p>
                <hr/>
                
                <Route exact path='/journals/:journalId'>
                    <h2>Radovi:</h2>
                    <PaperList 
                        papers={this.props.journal.papers} 
                        handleClick={this.props.handlePaperClick}/>
                </Route>
                <Route path='/journals/:journalId/papers/:paperId'>
                    <PaperView paper={this.props.paper}/>
                </Route>
            </div>
        );
    }
}
export default withRouter(JournalView);
