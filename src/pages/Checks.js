import { Component } from 'react';

class Checks extends Component{
    constructor() {
        super();
        this.state = { data: [], dishes: [], table_id:0, check_id:0, price:0.0, status:"", ids:""};
        this.submit = this.submit.bind(this);
        this.submitUpdate = this.submitUpdate.bind(this);
    }

    setData(ch){
        console.log(ch);
        let ans = ""
        let sum = 0;
        ch.dish_list.forEach((el) => {ans+=el.id+" "; sum += el.price})
        this.setState({check_id: ch.name});
        this.setState({price: sum});
        this.setState({status: ch.status});
        this.setState({ids: ans});
        this.setState({check_id: ch.id});
    }

    submitUpdate(event){
        if (this.state != null){
            try{
            fetch("http://localhost:5000/checks", {method: 'UPDATE', headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
            },
                body: JSON.stringify({status: this.state.status, ids: this.state.ids, id: parseInt(this.state.check_id), price: parseFloat(this.state.price)})
            }).then(el => alert("Update check"))}
            catch(e){alert("Cant update heck ("+e.toString())}
        }
        else alert("fill all data!")
        event.preventDefault();
    }

    submit(event){
        if (this.state.table_id > 0){
            try{
            fetch("http://localhost:5000/checks", {method: 'POST', headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
            },
                body: JSON.stringify({table_id:parseInt(this.state.table_id), empl_id:parseInt(localStorage.getItem("id"))})
            }).then(el => alert("Add new check"))}
            catch(e){alert("Cant add new check ("+e.toString())}
        }
        else alert("fill all data!")
        event.preventDefault();
    }
    
    componentDidMount() {
        fetch("http://localhost:5000/checks?q="+localStorage.getItem("id"), {method: 'GET'})
            .then(res => res.json())
            .then(text => this.setState({ data: text }));
        fetch("http://localhost:5000/dishes", {method: 'GET'})
            .then(res => res.json())
            .then(text => this.setState({ dishes: text }));
            
    }

    calcPrice(){
        let idss = this.state.ids.split(" ")
        let sum = 0;
        idss.forEach(el=>{
            var dish = this.state.dishes.find(ele => ele.id === parseInt(el))
            if (dish)
                sum += parseFloat(dish.price);
        })
        return sum;
    }

    render(){
        
        console.log(this.state);
        return (
            <div id="root" >
                {this.state.data.map(el =>{ 
                    let ans = ""
                    
                    console.log(el.dish_list)
                    el.dish_list.forEach(ele => ans+=ele.name+", ")
                    return (<p id={el.id} key={el.id}>
                    {"Number: "+el.id+", status: "+el.status+", employ ID: "+el.employee.id+", sum: "+el.sum+", all dishes: "+ans}</p>)})}
                <form onSubmit={this.submit}>
                    <label>
                        table ID:
                        <input type="text" value={this.state.table_id} onChange={(e)=>{this.setState({table_id: e.currentTarget.value})}} />
                    </label>
                    <input type="submit" value="Create check" />
                </form>
                <h4>Uppdate</h4>
                {this.state.data.map(el => (<button id={el.id} key={el.id} onClick={(e)=>{this.setState({prod: el}); this.setData(el);}}>{"Update with id "+el.id}</button>))}
                <form onSubmit={this.submitUpdate}>
                    <label>
                        status:
                        <input type="text" value={this.state.status} onChange={(e)=>{this.setState({status: e.currentTarget.value})}} />
                    </label>
                    <label>
                        dishes IDs:
                        <input type="text" value={this.state.ids} onChange={(e)=>{this.setState({ids: e.currentTarget.value}); this.setState({price: this.calcPrice()});}} />
                    </label>
                    <p>{"Price "+this.state.price}</p>
                    <input type="submit" value="Update" />
                </form>
            </div>
        );

    }
}

export default Checks;