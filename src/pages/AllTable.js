import { Component } from 'react';

class AllTable extends Component{
    constructor() {
        super();
        this.state = { data: [] };
    }
    
    componentDidMount() {
        const controller = new AbortController()
        const signal = controller.signal
        fetch("http://localhost:5000/tables", {method: 'GET',signal: signal,})
            .then(res => res.json())
            .then(text => this.setState({ data: text }));
        const timeoutId = setTimeout(() => controller.abort(), 1000)
    }

    render(){
        if (this.state.data != null){
        if (this.state.data.size < 1){return(<div id="root"><h3>Try again later</h3></div>)
        }
            return (
                <div id="root" >
                    {this.state.data.map(el => (<p key={el.id+el.from}>{"Number: "+el.id+", status: "+el.status_in_moment+", people can sit: "+el.sit+", from "+el.from+", description: "+el.description}</p>))}
                </div>
            );
        }
        else{
            return(<div id="root"><h3>Try again later</h3></div>)
        }

    }
}

export default AllTable;