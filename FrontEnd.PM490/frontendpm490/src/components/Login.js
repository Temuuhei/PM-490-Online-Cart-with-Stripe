
import React, { Component } from "react";
import {Link, Redirect, Route, Switch} from "react-router-dom";

import withContext from "../withContext";
import ProductList from "./ProductList";
import SignUp from "./SignUp";

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: ""
        };
        this.routerRef = React.createRef();
    }

    handleChange = e => this.setState({ [e.target.name]: e.target.value, error: "" });

    login = (e) => {
        e.preventDefault();

        const { username, password } = this.state;
        if (!username || !password) {
            return this.setState({ error: "Fill all fields!" });
        }
        this.props.context.login(username, password)
            .then((loggedIn) => {
                if (!loggedIn) {
                    this.setState({ error: "Invalid Credentails" });
                } else {
                    const token = this.props.context.token;
                }
            })
    };

    render() {
        return !this.props.context.user ? (
            <>
                <div className="hero is-info ">
                    <div className="hero-body container">
                        <h4 className="title">Login</h4>
                    </div>
                </div>
                <br />
                <br />
                <form onSubmit={this.login}>
                    <div className="columns is-mobile is-centered">
                        <div className="column is-one-third">
                            <div className="field">
                                <label className="label">Username: </label>
                                <input
                                    className="input"
                                    type="username"
                                    name="username"
                                    onChange={this.handleChange}
                                />
                            </div>
                            <div className="field">
                                <label className="label">Password: </label>
                                <input
                                    className="input"
                                    type="password"
                                    name="password"
                                    onChange={this.handleChange}
                                />
                            </div>
                            {this.state.error && (
                                <div className="has-text-danger">{this.state.error}</div>
                            )}
                            <div className="field is-clearfix">
                                <button
                                    className="button is-primary is-outlined is-pulled-right"
                                >
                                    Submit
                                </button>

                            </div>
                        </div>
                    </div>
                </form>
                <div className="columns is-mobile is-centered">
                    <Route ref={this.routerRef}>
                        <Link to="/signUp" className="navbar-item">
                            New User?
                        </Link>

                    </Route>
                </div>
            </>
        ) : (
            <Redirect
            to={{
              pathname: "/products",
              state: { token: this.props.context.token }
            }}
          />
        );
    }
}

export default withContext(Login);
