import { Component } from 'react';

class Register extends Component{
    constructor() {
        super();
        this.state = { email:"", password:"", phone:"", position:"", name:"" };
        this.register = this.register.bind(this);
    }
    
    componentDidMount() {
        
    }

    register(event){
        fetch("http://localhost:5000/register", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': 'Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers'
            },
            body: JSON.stringify(this.state)
            });
        event.preventDefault();
    }

    render(){
        if (localStorage.getItem("login") === "admin"){
            return (
                <div id="root" >
                    <form onSubmit={this.register}>
                        <label>
                            E-mail:
                            <input type="text" value={this.state.email} onChange={(e)=>{this.setState({email: e.currentTarget.value})}} />
                        </label>
                        <label>
                            Phone number:
                            <input type="text" value={this.state.phone} onChange={(e)=>{this.setState({phone: e.currentTarget.value})}} />
                        </label>
                        <label>
                            Full Name:
                            <input type="text" value={this.state.name} onChange={(e)=>{this.setState({name: e.currentTarget.value})}} />
                        </label>
                        <label>
                            Position:
                            <input type="text" value={this.state.position} onChange={(e)=>{this.setState({position: e.currentTarget.value})}} />
                        </label>
                        <label>
                            Password
                            <input type="password" value={this.state.password} onChange={(e)=>{this.setState({password: e.currentTarget.value})}} />
                        </label>
                        <input type="submit" value="Create" />
                    </form>
                </div>
            );
        }
        else{
            return(<div id="root"><h2>Not enough permissions. Contact the administrator.</h2></div>)
        }
    }
}

export default Register;
