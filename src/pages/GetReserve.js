import { Component } from 'react';

class GetReserve extends Component{
    constructor() {
        super();
        this.state = { data: []};
        this.clickDel = this.clickDel.bind(this);
    }

    clickDel(e){
        var id = parseInt(e.target.id)
        if (id > -1){
            fetch("http://localhost:5000/reserve", {method: 'DELETE', headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
            },
            body: JSON.stringify({id: id})
        }).then(el => alert("Reserve "+id+" delete"))}
    }
    
    componentDidMount() {
        fetch("http://localhost:5000/reserve", {method: 'GET'})
            .then(res => res.json())
            .then(text => this.setState({ data: text }));
    }

    render(){
        console.log(this.state.data);
        if (localStorage.getItem("login") !== "gest"){
            if (this.state.data != null && this.state.data.length>0){
                return (
                    <div id="root" >
                        {this.state.data.map(el => (<p key={el.id}>{"Id:"+el.id+", Data:"+el.date_time+", Name:"+el.full_name+", Phone:"+el.phone+", additional:"+el.special_description+
                        ", Table number:"+el.table.id+", time along:"+el.time_along}</p>))}
                        {this.state.data.map(el => (<button key={el.id} id={el.id} onClick={this.clickDel}>{el.id+" - delete"}</button>))}
                    </div>
                );
            }
            else{
                return(<div id="root"><h3>All free</h3></div>)
            }
        }
        else{return(<div id="root"><h2>Not enough permissions. Contact the administrator.</h2></div>)}
    }
}

export default GetReserve;