import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import withContext from "../withContext";
import axios from 'axios';

const initState = {
  username: "",
  password: "",
  fullname: "",
  phone: "",
  email: "",
  role: "",
  address: "",
  vendorInitialPayment : 0.0,
  flash: null,
};

class SignUp extends Component {
  constructor(props) {
    super(props);
    this.state = initState;
    this.routerRef = React.createRef();
  }

  handleChange = (e) =>
    this.setState({ [e.target.name]: e.target.value, error: "" });


  save = async (e) => {
    e.preventDefault();
    const {
      username,
      password,
      fullname,
      phone,
      email,
      role,
      address,
      vendorInitialPayment,
      

    } = this.state;

    if (!username || !password || !fullname || !phone || !email || !role) {
      this.setState({
        flash: { status: "is-danger", msg: "Fill all fields!" },
      });
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/signup",
        { username, password, email, role, address, fullname }
      );

      if (response.status === 200) {
        this.setState(initState);
        alert("FYI : All Vendor has to pay initial one time payment 20000$ Once you agreed we will charge your card later only one time ")
        this.setState({
          flash: {
            status: "is-success",
            msg: "User registered successfully",
          },
        });
        this.props.history.push('/login');
        
          
      } else {
        this.setState({
          flash: {
            status: "is-danger",
            msg: "An error occurred. Please try again.",
          },
        });
      }
    } catch (error) {
      this.setState({
        flash: {
          status: "is-danger",
          msg: "User already registered",
        },
      });
    }
  };

  render() {
    const { username, password, fullname, phone, email, role, address, vendorInitialPayment } =
      this.state;

    return (
      <>
        <div className="hero is-info ">
          <div className="hero-body container">
            <h4 className="title">Sign Up</h4>
          </div>
        </div>
        <br />
        <br />
        <form onSubmit={this.save}>
          <div className="columns is-mobile is-centered">
            <div className="column is-one-third">
              <div className="field">
                <label className="label">Username: </label>
                <input
                  className="input"
                  type="username"
                  name="username"
                  value={username}
                  onChange={this.handleChange}
                />
              </div>
              <div className="field">
                <label className="label">Password: </label>
                <input
                  className="input"
                  type="password"
                  name="password"
                  value={password}
                  onChange={this.handleChange}
                />
              </div>
              <div className="field">
                <label className="label">Full Name: </label>
                <input
                  className="input"
                  type="fullname"
                  name="fullname"
                  value={fullname}
                  onChange={this.handleChange}
                />
              </div>
              <div className="field">
                <label className="label">Phone: </label>
                <input
                  className="input"
                  type="number"
                  name="phone"
                  value={phone}
                 

                                    onChange={this.handleChange}
                                />
                            </div>
                            <div className="field">
                                <label className="label">Email: </label>
                                <input
                                    className="input"
                                    type="email"
                                    name="email"
                                    value={email}
                                    onChange={this.handleChange}
                                />
                            </div>
                            <div className="field">
                                <label className="label">Address: </label>
                                <input
                                    className="input"
                                    type="address"
                                    name="address"
                                    value={address}
                                    onChange={this.handleChange}
                                />
                            </div>
                            <div className="field">
                                <label className="label">Role: </label>
                                <select name="role"
                                        className="input"
                                        onChange={this.handleChange}
                                        value={role}>
                                    <option value="CUSTOMER">Customer</option>
                                    <option value="VENDOR">Vendor</option>
                                </select>
                            </div>
                            {role === "VENDOR" && (
                            <div className="field">
                              <label className="label">Initial payment: </label>
                              <input
                                className="input"
                                type="number"
                                name="vendorInitialPayment"
                                value={vendorInitialPayment}
                                onChange={this.handleChange}
                              />
                              <label>
                            <input
                              type="checkbox"
                              name="agreement"
                              checked={this.state.agreement}
                              onChange={this.handleChange}
                              required
                            />
                            <span>  </span>I agree to pay the initial payment.
                          </label>
                            </div>
                          )}

                            {this.state.flash && (
                                <div className={`notification ${this.state.flash.status}`}>
                                    {this.state.flash.msg}
                                </div>
                            )}
                            <div className="field is-clearfix">
                                <button
                                    className="button is-primary is-outlined is-pulled-right"
                                    type="submit"
                                    onClick={this.save}
                                >
                                    Submit
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </>

        );
    }
}

export default withContext(SignUp);
