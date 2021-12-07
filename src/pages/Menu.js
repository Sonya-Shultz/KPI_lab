import { Component } from 'react';

class Menu extends Component{
    constructor() {
        super();
        this.state = { data: [] };
        this.click = this.click.bind(this);
    }
    
    componentDidMount() {
        var date = new Date()
        fetch("http://localhost:5000/menu?q="+date.toISOString(), {method: 'GET'})
            .then(res => res.json())
            .then(text => this.setState({ data: text }));
    }

    click(event){
        let status = "NOT OK"
        if (event.target.name !== "OK") status="OK"
        fetch("http://localhost:5000/menu", {
            method: 'UPDATE',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
            },
            body: JSON.stringify({id: event.target.id, status: status})
            })
            .then(ans => ans.json()).then(text => alert(text));
        event.preventDefault();
    }

    render(){
        console.log(this.state.data);
        var data_arr = []
        this.state.data.forEach(el => {
            var all_str = ""
            el.dish.products_list.forEach(ele => {
                all_str += ele.name + " ";
            })
            data_arr.push(all_str);
        })
        return (
            <div id="root" >
                {this.state.data.map(el => (<p key={el.id} >{"Id: "+el.id+", status: "+el.status+", name: "+el.dish.name+", Products:"+data_arr[this.state.data.indexOf(el)]}</p>))}
                {this.state.data.map(el => {
                    if (el.status ==="OK") {return (<button onClick={this.click} key={el.id} id={el.id} name={el.status}>{"Set status NOT OK for id "+el.id}</button>)}
                    else {return (<button onClick={this.click} key={el.id} id={el.id} name={el.status}>{"Set satus OK for id "+el.id}</button>)}
                })}
            </div>
        );

    }
}

export default Menu;