import { Component } from 'react';

class SearchList extends Component{
    constructor() {
        super();
        this.state = { data: [] };
    }
    
    componentDidMount() {
        var currentLocation = window.location.pathname+window.location.search;
        console.log(currentLocation)
        fetch("http://localhost:5000"+currentLocation, {method: 'GET'})
            .then(res => res.json())
            .then(text => this.setState({ data: text }));
    }

    render(){
        return (
            <div id="root" >
                {this.state.data.map(el => (<p key={el.id}>{"Number: "+el.id+", status: "+el.status_in_moment+", people can sit: "+el.sit+", from "+el.from+", description: "+el.description}</p>))}
            </div>
        );

    }
}

export default SearchList;
