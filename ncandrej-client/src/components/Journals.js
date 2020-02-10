import React, { Component } from 'react';
import { Route, Redirect, withRouter } from 'react-router-dom';
import JournalList from './JournalList';
import JournalView from './JournalView';

class Journals extends Component {
    constructor(props) {
        super(props);
        this.state={
            journals: [],
            journal: null,
            paper: null,
        };
        this.handleJournalClick = this.handleJournalClick.bind(this);
        this.handlePaperClick = this.handlePaperClick.bind(this);
        this.setSubscribed = this.setSubscribed.bind(this);
    }
    componentDidMount() {
        window.axioss.get('/journals')
            .then(json => {
                this.setState({journals: json.data}); 
            });
    }
    handleJournalClick(journal) {
        this.setState({journal: journal});
        this.props.history.push('/journals/'+journal.id);
        window.axioss.get('journals/'+journal.id+'/papers')
            .then(json => {
                let journal = this.state.journal;
                journal['papers'] = json.data;
                this.setState({journal: journal});
            });
    }
    handlePaperClick(paper) {
        this.setState({paper: paper});
        this.props.history.push('/journals/'+this.state.journal.id+'/papers/'+paper.id);
        window.axioss.get('/papers/' + paper.id)
            .then(json => {
                let p = this.state.paper;
                p['status']=json.data.status;
                p['redirectUrl']=json.data.redirectUrl;
                this.setState({paper: p});
            });
    }
    setSubscribed(isSubscribed) {
        let journal = this.state.journal;
        journal['subscribed']=isSubscribed;
        this.setState({journal: journal});
    }
    render() {
        return (
            <div className='container mt-3'>
                <Route path='/journals/:journalId'>
                    <JournalView 
                        journal={this.state.journal} 
                        setSubscribed={this.setSubscribed} 
                        handleJournalClick={this.handleJournalClick}
                        handlePaperClick={this.handlePaperClick}
                        paper={this.state.paper}/>
                </Route>
                <Route exact path='/journals'>
                    <JournalList 
                        journals={this.state.journals} 
                        handleClick={this.handleJournalClick}/>
                </Route>
            </div>
        );
    }
}
export default withRouter(Journals);