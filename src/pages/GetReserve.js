import { Component } from 'react';

function compSecFetch(t, id, desc){
    try{
    if(t>10) return;
    fetch("http://localhost:5010/reserve", {method: 'DELETE', headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
            },
            body: JSON.stringify({id: id,special_description: desc })
        }).then(el => alert("Reserve "+id+" delete"))
    } catch(e){alert("Cant reserve table ("+e.toString()); compSecFetch(t++, id);}
}
function compFrFetch(t, id, desc){
    try{
    if(t>10) return;
    fetch("http://localhost:5005/reserve", {method: 'DELETE', headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
            },
            body: JSON.stringify({id: id, special_description: desc})
        }).then(el => alert("Reserve "+id+" delete"))
    } catch(e){alert("Cant reserve table ("+e.toString()); compFrFetch(t++, id);}
}
function myCompFetch(t, id, desc){
    try{
    if(t>10) return;
    fetch("http://localhost:5000/reserve", {method: 'DELETE', headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
            },
            body: JSON.stringify({id: id, special_description: desc})
        }).then(el => alert("Reserve "+id+" delete"))
    } catch(e){alert("Cant reserve table ("+e.toString()); myCompFetch(t++, id);}
}

class GetReserve extends Component{
    constructor() {
        super();
        this.state = { data: [], from:""};
        this.clickDel = this.clickDel.bind(this);
    }

    clickDel(e){
        var id = parseInt(e.target.id.split("&")[0])
        var desc =e.target.id.split("&")[1]
        if (id > -1){
            if (desc == "Sec sup"){
                compSecFetch(0, id,desc)
            }
            else if (desc == "First sup"){
                compFrFetch(0, id,desc)
            }
            else {
                myCompFetch(0, id,desc)
            }
        }
    }
    
    componentDidMount() {
        this.setState({data :[]});
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
                        {this.state.data.map(el => (<p key={el.id+el.special_description}>{"Id:"+el.id+", Data:"+el.date_time+", Name:"+el.full_name+", Phone:"+el.phone+", additional:"+el.special_description+
                        ", Table number:"+el.table.id+", time along:"+el.time_along}</p>))}
                        {this.state.data.map(el => (<button key={el.id+el.special_description} id={el.id+"&"+el.table.from}  onClick={this.clickDel}>{el.id+" - delete "+el.special_description}</button>))}
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