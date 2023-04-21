
import React, {Component} from "react";
import {Switch, Route, Link, BrowserRouter as Router} from "react-router-dom";
import axios from 'axios';
import jwt_decode from 'jwt-decode';

import CreateProduct from './components/CreateProduct';
import Login from './components/Login';
import ProductList from './components/ProductList';
import SignUp from './components/SignUp';
import Context from "./Context";
import SearchProducts from './components/SearchProducts';
import ShoppingCart from "./components/ShoppingCart";
import PaymentMethod from "./components/PaymentMethod";
import UsersManagement from "./components/UsersManagement";
import Finance from "./components/Finance";

export default class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null,
            cart: {},
            search:null,
            products: []
        };
        this.routerRef = React.createRef();
    }

    async componentDidMount() {
        let user = localStorage.getItem("user");
        let cart = localStorage.getItem("cart");
        let products = await axios.get('http://localhost:8080/api/product/');
       // let search = localStorage.getItem("search");
        user = user ? JSON.parse(user) : null;
        cart = cart ? JSON.parse(cart) : {};

        this.setState({user, products: products.data, cart});
    }
    addToCart=cartItem=>{
        let cart=this.state.cart;
        if(cart[cartItem.id]){
            cart[cartItem.id].amount+=cartItem.amount;
        }else{
            cart[cartItem.id]=cartItem;
        }
        if(cart[cartItem.id].amount>cart[cartItem.id].product.stock){
            cart[cartItem.id].amount=cart[cartItem.id].product.stock;
        }
        localStorage.setItem("cart",JSON.stringify(cart));
        this.setState({cart});
    };

    removeFromCart = cartItemId => {
        let cart=this.state.cart;
        delete cart[cartItemId];
        localStorage.setItem("cart",JSON.stringify(cart));
        this.setState({cart});
    };

    clearCart=()=>{
        let cart={};
        localStorage.removeItem("cart");
        this.setState({cart});
    };

    checkout=()=>{
        if(this.state.user){
            this.routerRef.current.history.push("/PaymentMethod");
            return;
        }
        if(!this.state.user){
            this.routerRef.current.history.push("/login");
            return;
        }//with out login checkout will redirect to login page

        const cart=this.state.cart;

        const products=this.state.products.map(p=>{
            if(cart[p.name]){
                p.stock=p.stock-cart[p.name].amount;

                axios.put(
                    `http://localhost:8080/api/product/${p.id}`,
                    {...p},
                )
            }
            return p;
        });

        this.setState({products});
        this.clearCart();
    };

    search = async (name, status, color, idVendor, idCategory) => {
        console.log({name,status, color, idVendor, idCategory});
        const res = await axios.post(
            'http://localhost:8080/api/product/advancesearch',
            {name, status, color, idVendor, idCategory},
        ).catch((res) => {
            return {message:"#No products found!"}
        })
        console.log(JSON.stringify(res));
        if(res.status === 200) {
            console.log("Response's data :  :  : " + res.data);
            this.setState({products: res.data});
        }
    }

    login = async (username, password) => {
        const res = await axios.post(
            'http://localhost:8080/api/auth/signin',
            {username, password},
        ).catch((res) => {
            return {status: 401, message: 'Unauthorized'}
        })

        if(res.status === 200) {
            const { username } = jwt_decode(res.data.accessToken)
            //console.log("###################"+res.data.role);
            const user = {
                id: res.data.id,
                username : res.data.username,
                role: res.data.role,
                token: res.data.accessToken,
               // accessLevel: email === 'admin@example.com' ? 0 : 1
                // role: res.data.role === 'VENDOR' ? 0 : 1
                 //role: res.data.role === 'VENDOR' ? 0 : 1
            }

            this.setState({user});
            localStorage.setItem("user", JSON.stringify(user));
            console.log("###################"+user.token);
            return true;
        } else {
            return false;
        }

    }

    createProduct = (product, callback) => {
         /*
        let products = this.state.products.slice();
        products.push(product);
        this.setState({ products }, () => callback && callback());
         */
    };
    logout = e => {
        e.preventDefault();
        this.setState({user: null});
        localStorage.removeItem("user");
        this.routerRef.current.history.push("/login"); // redirect to login page
    };

    render() {
        return (
            <Context.Provider
                value={{
                    ...this.state,
                    removeFromCart: this.removeFromCart,
                    addToCart: this.addToCart,
                    login: this.login,
                    signup: this.signUp,
                    createProduct: this.createProduct,
                    clearCart: this.clearCart,
                    checkout: this.checkout,
                    search: this.search
                }}
            >
                <Router ref={this.routerRef}>
                    <div className="App">
                        <nav
                            className="navbar container"
                            role="navigation"
                            aria-label="main navigation"
                        >
                            <div className="navbar-brand">
                                <b className="navbar-item is-size-4 ">Five Layers Store</b>
                                <label
                                    role="button"
                                    class="navbar-burger burger"
                                    aria-label="menu"
                                    aria-expanded="false"
                                    data-target="navbarBasicExample"
                                    onClick={e => {
                                        e.preventDefault();
                                        this.setState({showMenu: !this.state.showMenu});
                                    }}
                                >
                                    <span aria-hidden="true"></span>
                                    <span aria-hidden="true"></span>
                                    <span aria-hidden="true"></span>
                                </label>
                            </div>
                            <div className={`navbar-menu ${
                                this.state.showMenu ? "is-active" : ""
                            }`}>
                                <Link to="/products" className="navbar-item">
                                    Products
                                </Link>
                                {this.state.user && this.state.user.role === "VENDOR" && (
                                    <Link to="/CreateProduct" className="navbar-item">
                                        Create Product
                                    </Link>
                                )}
                                <Link to="/ShoppingCart" className="navbar-item">
                                    ShoppingCart
                                    <span
                                        className="tag is-primary"
                                        style={{marginLeft: "5px"}}
                                    >
                                        {Object.keys(this.state.cart).length}
                                    </span>
                                </Link>
                                {this.state.user && this.state.user.role === "ADMIN" && (
                                    <Link to="/UserManagement" className="navbar-item">
                                        User Management
                                    </Link>
                                )}
                                {this.state.user && this.state.user.role === "ADMIN" && (
                                    <Link to="/finance" className="navbar-item">
                                        Finance
                                    </Link>
                                )}                                {!this.state.user ? (
                                    <Link to="/login" className="navbar-item">
                                        Login
                                    </Link>
                                ) : (
                                    <Link to="/" onClick={this.logout} className="navbar-item">
                                        Logout
                                    </Link>

                                )}
                            </div>
                        </nav>
                        <SearchProducts />
                        <Switch>
                            <Route exact path="/" component={ProductList} />
                            <Route exact path="/CreateProduct" component={CreateProduct}/>
                            <Route exact path="/login" component={Login} />
                            <Route exact path="/UserManagement" component={UsersManagement} />
                            <Route exact path="/signup" component={SignUp}/>
                            <Route exact path="/products" component={ProductList} />
                            <Route exact path="/ShoppingCart" component={ShoppingCart}/>
                            <Route exact path="/PaymentMethod" component={PaymentMethod}/>
                            <Route exact path="/finance" component={Finance}/>
                        </Switch>
                    </div>
                </Router>
            </Context.Provider>
        );
    }
}
