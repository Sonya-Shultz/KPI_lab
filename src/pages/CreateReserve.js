import { Component } from 'react';

class CreateReserve extends Component{
    constructor() {
        super();
        this.state = { data: [], table_id: null, date_time:"", time_along:"", phone:"", full_name:"", special_description:"" };
        this.setReserve = this.setReserve.bind(this);
        this.submit = this.submit.bind(this);
    }
    
    componentDidMount() {
        var date = new Date();
        fetch("http://localhost:5000/reserveSet?q="+date.toISOString().substring(0,10), {method: 'GET'})
            .then(res => res.json())
            .then(text => this.setState({ data: text }));
    }
    setReserve(event){
        this.setState({table_id: event.target.id});
        console.log(this.state.table_id);
    }

    submit(event){
        if (this.state.table_id != null){
            try{
            fetch("http://localhost:5000/reserveSet", {method: 'POST', headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
            },
                body: JSON.stringify(this.state)
            }).then(el => alert("Reserve complite"))}
            catch(e){alert("Cant reserve table ("+e.toString())}
        }
        else alert("Chose table first!")
        event.preventDefault();
    }

    render(){
        if (this.state.data != null){
            return (
                <div id="root" >
                    <h3>You table is </h3><p >{this.state.table_id}</p>
                    {this.state.data.map(el => (
                        <p key={el.id+el.from}>{"Number: "+el.id+", status: "+el.status_in_moment+", people can sit: "+el.sit+", from "+el.from+", description: "+el.description}</p>
                    ))}
                    {this.state.data.map(el => (
                        <button key={el.id} onClick={(e)=>{this.setState({table_id: el.id}); console.log(el.id)}}>{"Number: "+el.id+" set reserve"}</button>
                    ))}
                    <form onSubmit={this.submit}>
                        <label>
                            Phone number:
                            <input type="text" value={this.state.phone} onChange={(e)=>{this.setState({phone: e.currentTarget.value})}} />
                        </label>
                        <label>
                            Full Name:
                            <input type="text" value={this.state.full_name} onChange={(e)=>{this.setState({full_name: e.currentTarget.value})}} />
                        </label>
                        <label>
                            Special description:
                            <input type="text" value={this.state.special_description} onChange={(e)=>{this.setState({special_description: e.currentTarget.value})}} />
                        </label>
                        <label>
                            Time along:
                            <input type="text" value={this.state.time_along} onChange={(e)=>{this.setState({time_along: e.currentTarget.value})}} />
                        </label>
                        <label>
                            Date (yyyy-mm-dd):
                            <input type="text" value={this.state.date_time} onChange={(e)=>{this.setState({date_time: e.currentTarget.value})}} />
                        </label>
                        <input type="submit" value="Create" />
                    </form>
                </div>
            );
        }
        else{
            return(<div id="root"><h3>Incorrect number</h3></div>)
        }

    }
}

export default CreateReserve;