import { Component } from 'react';

class AllTable extends Component{
    constructor() {
        super();
        this.state = { data: [] };
    }
    
    componentDidMount() {
        fetch("http://localhost:5000/tables", {method: 'GET'})
            .then(res => res.json())
            .then(text => this.setState({ data: text }));
    }

    render(){
        if (this.state.data != null){
            return (
                <div id="root" >
                    {this.state.data.map(el => (<p key={el.id+el.from}>{"Number: "+el.id+", status: "+el.status_in_moment+", people can sit: "+el.sit+", from "+el.from+", description: "+el.description}</p>))}
                </div>
            );
        }
        else{
            return(<div id="root"><h3>Incorrect number</h3></div>)
        }

    }
}

export default AllTable;