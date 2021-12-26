import { Component } from 'react';

class Login extends Component{
    constructor() {
        super();
        this.state = { data: {}, email:"", password:"" };
        this.login = this.login.bind(this);
    }
    
    componentDidMount() {
        
    }

    login(event){
        try{
            console.log("try")
            fetch("http://localhost:5000/login?query="+this.state.email+"&"+this.state.password, {method: 'GET'})
                .then(res => res.json()).then(el =>{
                    if (el != null) {
                        console.log(el.position, el.id)
                        localStorage.setItem("login", el.position);
                        localStorage.setItem("id", el.id);
                        alert("You connect like "+el.position);
                    }
                    else {
                        localStorage.setItem("login", "gest")
                        localStorage.setItem("id", -1);
                        alert("You connect like gest");
                    };
            }).catch(e=>{
                localStorage.setItem("login", "gest")
                localStorage.setItem("id", -1);
                alert("You connect like gest");
            })
            event.preventDefault();
        }
        catch(e){
            localStorage.setItem("login", "gest")
            localStorage.setItem("id", -1);
            alert("You connect like gest");
        }
    }

    render(){
        return (
            <div id="root" >
                <form onSubmit={this.login}>
                    <label>
                        E-mail:
                        <input type="text" value={this.state.email} onChange={(e)=>{this.setState({email: e.target.value})}} />
                    </label>
                    <label>
                        Password
                        <input type="password" value={this.state.password} onChange={(e)=>{this.setState({password: e.target.value})}} />
                    </label>
                    <input type="submit" value="Login" />
                </form>
            </div>
        );

    }
}

export default Login;
