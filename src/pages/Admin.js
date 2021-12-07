import { Component } from 'react';

class Admin extends Component{
    constructor() {
        super();
        this.prod = this.prod.bind(this);
        this.resrv = this.resrv.bind(this);
        this.fin = this.fin.bind(this);
        
        let tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate()+1);
        
        this.state = { reserves: [] , products:[], finance:[], day: new Date().toISOString().substring(0,10), nextDay: tomorrow.toISOString().substring(0,10)};
        console.log(this.state.day, this.state.nextDay)
    }
    
    componentDidMount() {
    }

    prod(e){fetch("http://localhost:5000/productList", {method: 'GET'})
    .then(res => res.json())
    .then(text => this.setState({ products: text }));}

    resrv(e){fetch("http://localhost:5000/reserveNext?q="+this.state.nextDay, {method: 'GET'})
    .then(res => res.json())
    .then(text => this.setState({ reserves: text }));}

    fin(e){fetch("http://localhost:5000/dayBills?q="+this.state.day, {method: 'GET'})
    .then(res => res.json())
    .then(text => this.setState({ finance: text }));}

    calcPrice(){
        let sum = 0;
        this.state.finance.forEach(el=>{
            sum += parseFloat(el.sum);
        })
        return sum;
    }

    render(){
        console.log(this.state);
        return (
            <div id="root" >
                <h4>Options</h4>
                <button onClick={this.prod}>Create product list</button>
                <button onClick={this.resrv}>Create reserves for tomorow</button>
                <button onClick={this.fin}>Crate financial</button>
                <h4>Reserves for tomorow</h4>
                {this.state.reserves.map(el => (<p key={el.id}>{"Id:"+el.id+", Data:"+el.date_time+", Name:"+el.full_name+", Phone:"+el.phone+", additional:"+el.special_description+
                        ", Table number:"+el.table.id+", time along:"+el.time_along}</p>))}
                <h4>Products list</h4>
                {this.state.products.map(el => (<p id={el.id} key={el.id}>
                    {"Number: "+el.id+", status: "+el.status+", name: "+el.name+", type: "+el.pr_type+", amount: "+el.amount}</p>))}
                <h4>{"Finance (total - "+this.calcPrice()+")"}</h4>
                {this.state.finance.map(el =>{ 
                    let ans = ""
                    
                    console.log(el.dish_list)
                    el.dish_list.forEach(ele => ans+=ele.name+", ")
                    return (<p id={el.id} key={el.id}>
                    {"Number: "+el.id+", status: "+el.status+", employ ID: "+el.employee.id+", sum: "+el.sum+", all dishes: "+ans}</p>)})}
            </div>
        );

    }
}

export default Admin;