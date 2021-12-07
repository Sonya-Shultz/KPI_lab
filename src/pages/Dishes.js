import { Component } from 'react';

class Dishes extends Component{
    constructor() {
        super();
        this.state = { data: [], name: "", name_u:"", ids_u:[], price:0.0, price_u:0.0,type:"", type_u:"", desc:"", desc_u:"", el:null };
        this.click=this.click.bind(this);
        this.submit=this.submit.bind(this);
        this.submitUpdate=this.submitUpdate.bind(this);
    }

    submit(event){
        fetch("http://localhost:5000/dishes", {method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
        },
        body: JSON.stringify({ name: this.state.name, price: parseFloat(this.state.price), type: this.state.price, description: this.state.desc})
        })
        .then(ans => ans.json()).then(text => alert(text));
        event.preventDefault();
    }
    submitUpdate(event){
        fetch("http://localhost:5000/dishes", {method: 'UPDATE',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
        },
        body: JSON.stringify({id: this.state.el.id, ids: this.state.ids_u, name: this.state.name_u, price: parseFloat(this.state.price_u), type: this.state.type_u, description: this.state.desc_u})
        })
        .then(ans => ans.json()).then(text => alert(text));
        event.preventDefault();
    }

    setData(el){
        let ans = ""
        el.products_list.forEach((el) => ans+=el.id+" ")
        this.setState({name_u: el.name})
        this.setState({ids_u: ans})
        this.setState({price_u: el.price})
        this.setState({desc_u: el.description})
        this.setState({type_u: el.dish_type})
        console.log(ans);
    }

    click(event){
        fetch("http://localhost:5000/menu", {method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
        },
        body: JSON.stringify({dish_id: event.target.id})
        })
        .then(ans => ans.json()).then(text => alert(text));
        event.preventDefault();
    }
    
    componentDidMount() {
        fetch("http://localhost:5000/dishes", {method: 'GET'})
            .then(res => res.json())
            .then(text => this.setState({ data: text }));
    }

    render(){
        console.log(this.state.data);
        var data_arr = []
        this.state.data.forEach(el => {
            var all_str = ""
            el.products_list.forEach(ele => {
                all_str += ele.name + " ";
            })
            data_arr.push(all_str);
        })
        return (
            <div id="root" >
                {this.state.data.map(el => (<p key={el.id}>{"Number: "+el.id+", name: "+el.name+", Products:"+data_arr[this.state.data.indexOf(el)]+", Price: "+el.price+", Type: "+el.dish_type+", desc: "+el.description}</p>))}
                {this.state.data.map(el => (<button key={el.id} id={el.id} onClick={this.click}>{"Add to tomorow menu dish with id "+el.id}</button>))}
                <h4>Add new</h4>
                <form onSubmit={this.submit}>
                    <label>
                        name:
                        <input type="text" value={this.state.name} onChange={(e)=>{this.setState({name: e.currentTarget.value})}} />
                    </label>
                    <label>
                        price:
                        <input type="text" value={this.state.price} onChange={(e)=>{this.setState({price: e.currentTarget.value})}} />
                    </label>
                    <label>
                        type:
                        <input type="text" value={this.state.type} onChange={(e)=>{this.setState({type: e.currentTarget.value})}} />
                    </label>
                    <label>
                        desc:
                        <input type="text" value={this.state.desc} onChange={(e)=>{this.setState({desc: e.currentTarget.value})}} />
                    </label>
                    <input type="submit" value="Create" />
                </form>
                <h4>Uppdate</h4>
                {this.state.data.map(el => (<button id={el.id} key={el.id} onClick={(e)=>{this.setState({el: el}); this.setData(el);}}>{"Update with id "+el.id}</button>))}
                <form onSubmit={this.submitUpdate}>
                    <label>
                        name:
                        <input type="text" value={this.state.name_u} onChange={(e)=>{this.setState({name_u: e.currentTarget.value})}} />
                    </label>
                    <label>
                        products Ids:
                        <input type="text" value={this.state.ids_u} onChange={(e)=>{this.setState({ids_u: e.currentTarget.value})}} />
                    </label>
                    <label>
                        price:
                        <input type="text" value={this.state.price_u} onChange={(e)=>{this.setState({price_u: e.currentTarget.value})}} />
                    </label>
                    <label>
                        type:
                        <input type="text" value={this.state.type_u} onChange={(e)=>{this.setState({type_u: e.currentTarget.value})}} />
                    </label>
                    <label>
                        desc:
                        <input type="text" value={this.state.desc_u} onChange={(e)=>{this.setState({desc_u: e.currentTarget.value})}} />
                    </label>
                    <input type="submit" value="Update" />
                </form>
            </div>
        );

    }
}

export default Dishes;