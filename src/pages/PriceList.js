import { Component } from 'react';

class PriceList extends Component{
    constructor() {
        super();
        this.state = { data: [] };
    }
    
    componentDidMount() {
        fetch("http://localhost:5000/price-list", {method: 'GET'})
            .then(res => res.json())
            .then(text => this.setState({ data: text }));
    }

    render(){
        console.log(this.state.data);
        return (
            <div id="root" >
                {this.state.data.map(el => (<p key={el.id}>{"Number: "+el.id+", status: "+el.status_in_moment+", people can sit: "+el.sit+", from "+el.from}</p>))}
            </div>
        );

    }
}

export default PriceList;
