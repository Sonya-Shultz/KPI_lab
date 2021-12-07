import { Component } from 'react';

class DetailsPrice extends Component{
    constructor() {
        super();
        this.state = { data: {} };
    }
    
    componentDidMount() {
        var currentLocation = window.location.pathname;
        fetch("http://localhost:5000"+currentLocation, {method: 'GET'})
            .then(res => res.json())
            .then(text => this.setState({ data: text }));
    }

    render(){
        if (this.state.data != null){
            return (
                <div id="root" >
                    <p>{"Number is "+this.state.data.id}</p>
                    <p>{"Status is "+this.state.data.status_in_moment}</p>
                    <p>{"Description: "+this.state.data.description}</p>
                    <p>{"People can sit "+this.state.data.sit}</p>
                </div>
            );
        }
        else{
            return(<div id="root"><h3>Incorrect number</h3></div>)
        }

    }
}

export default DetailsPrice;

