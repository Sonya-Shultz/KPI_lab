import { Component } from 'react';

class Products extends Component{
    constructor() {
        super();
        this.state = { data: [], pr_name:"", pr_status:"",pr_type:"", amount:0.0, prod:null, pr_name_u:"", pr_status_u:"",pr_type_u:"", amount_u:0.0, id:0 };
        this.submit = this.submit.bind(this);
        this.submitUpdate = this.submitUpdate.bind(this);
    }

    setData(prod){
        this.setState({pr_name_u: prod.name});
        this.setState({pr_type_u: prod.pr_type});
        this.setState({pr_status_u: prod.status});
        this.setState({amount_u: prod.amount});
        this.setState({id: prod.id});
    }

    submitUpdate(event){
        if (this.state.prod != null){
            try{
            fetch("http://localhost:5000/products", {method: 'UPDATE', headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
            },
                body: JSON.stringify({pr_name:this.state.pr_name_u, pr_status:this.state.pr_status_u,pr_type:this.state.pr_type_u, amount:parseFloat(this.state.amount_u), id: this.state.id})
            }).then(el => alert("Update product"))}
            catch(e){alert("Cant update new product ("+e.toString())}
        }
        else alert("fill all data!")
        event.preventDefault();
    }

    submit(event){
        if (this.state.pr_name !== "" && this.state.pr_status !== "" && this.state.pr_type !== "" && this.state.amount >= 0){
            try{
            fetch("http://localhost:5000/products", {method: 'POST', headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
            },
                body: JSON.stringify({pr_name:this.state.pr_name, pr_status:this.state.pr_status,pr_type:this.state.pr_type, amount:parseFloat(this.state.amount)})
            }).then(el => alert("Ann new product"))}
            catch(e){alert("Cant add new product ("+e.toString())}
        }
        else alert("fill all data!")
        event.preventDefault();
    }
    
    componentDidMount() {
        fetch("http://localhost:5000/products", {method: 'GET'})
            .then(res => res.json())
            .then(text => this.setState({ data: text }));
    }

    render(){
        return (
            <div id="root" >
                {this.state.data.map(el => (<p id={el.id} key={el.id}>
                    {"Number: "+el.id+", status: "+el.status+", name: "+el.name+", type: "+el.pr_type+", amount: "+el.amount}</p>))}
                <form onSubmit={this.submit}>
                    <label>
                        name:
                        <input type="text" value={this.state.pr_name} onChange={(e)=>{this.setState({pr_name: e.currentTarget.value})}} />
                    </label>
                    <label>
                        status:
                        <input type="text" value={this.state.pr_status} onChange={(e)=>{this.setState({pr_status: e.currentTarget.value})}} />
                    </label>
                    <label>
                        type:
                        <input type="text" value={this.state.pr_type} onChange={(e)=>{this.setState({pr_type: e.currentTarget.value})}} />
                    </label>
                    <label>
                        amount:
                        <input type="text" value={this.state.amount} onChange={(e)=>{this.setState({amount: e.currentTarget.value})}} />
                    </label>
                    <input type="submit" value="Create" />
                </form>
                <h4>Uppdate</h4>
                {this.state.data.map(el => (<button id={el.id} key={el.id} onClick={(e)=>{this.setState({prod: el}); this.setData(el);}}>{"Update with id "+el.id}</button>))}
                <form onSubmit={this.submitUpdate}>
                    <label>
                        name:
                        <input type="text" value={this.state.pr_name_u} onChange={(e)=>{this.setState({pr_name_u: e.currentTarget.value})}} />
                    </label>
                    <label>
                        status:
                        <input type="text" value={this.state.pr_status_u} onChange={(e)=>{this.setState({pr_status_u: e.currentTarget.value})}} />
                    </label>
                    <label>
                        type:
                        <input type="text" value={this.state.pr_type_u} onChange={(e)=>{this.setState({pr_type_u: e.currentTarget.value})}} />
                    </label>
                    <label>
                        amount:
                        <input type="text" value={this.state.amount_u} onChange={(e)=>{this.setState({amount_u: e.currentTarget.value})}} />
                    </label>
                    <input type="submit" value="Update" />
                </form>
            </div>
        );

    }
}

export default Products;