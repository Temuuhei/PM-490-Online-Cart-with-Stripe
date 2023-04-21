import React, { Component } from "react";
import Stripe from "react-stripe-checkout";
import withContext from "../withContext";
import CartItem from "./CartItem";
import axios from 'axios';

const initState = {
    subtotal: 0,
    tax: 0,
    total: 0
};

class ShoppingCart extends Component {
    constructor(props) {
        super(props);
        this.state = initState;
    }

    componentDidMount() {
        const {cart} = this.props.context;
        const cartKeys = Object.keys(cart || {});
        let subt = 0;
        cartKeys.forEach(key => {subt += (cart[key].product.price * cart[key].amount)} );
        let tx = subt* 0.07;
        let tot = subt+ tx;
        this.setState({subtotal:subt})
        this.setState({tax : tx.toFixed(2)});
        this.setState({total : tot});

        console.log("@@@@@@" + this.subtotal);
        return cart;
    };


    handleToken = async (token) => {
        const { user } = this.props.context;
        console.log("Total is : " + this.state.total);
        console.log("Token  : " + token);
        try {
            console.log("Total is : " + this.state.total);
          const res = await axios.post(
            "http://localhost:8080/api/payment/charge",
            {
              amount: this.state.total, // Replace with the actual amount to charge
              token,
            },
            {
              headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + user.token,
                amount: this.state.total,
                token : token.id
              },
            }
          );
      
          if (res.status === 200) {
            alert("Payment Success");
            this.props.context.clearCart();
            this.props.history.push("/ShoppingCart");
          }
        } catch (error) {
          alert(error.response.data.message);
        }
      };




    render() {
        const {cart} = this.props.context;
        const cartKeys = Object.keys(cart || {});
        //console.log("######" + cartKeys);
        //console.log("@@@@@@" + cart[3].product.price);
        let subtotal = 0;
        return (
            <>
                <div className="hero is-info">
                    <div className="hero-body container">
                        <h4 className="title"> My Cart </h4>
                    </div>
                </div>
                <br/>
                <div className="container">
                    {cartKeys.length ? (
                        <div className="column columns is-multiline">
                            {
                                cartKeys.map(key => (
                                    <CartItem
                                        cartKey={key}
                                        key={key}
                                        cartItem={cart[key]}
                                        context={this.props.context}

                                    />

                                ))}
                            <div className="column is-12is-clearfix">
                                <br/>
                                <div className="is-pulled-right">
                                    <div className="container is-bordered">

                                        <span>Subtotal = </span><small>{this.state.subtotal}</small>
                                        <br/>
                                        <span>Tax = </span><small>{this.state.tax}</small>
                                        <br/>
                                        <span>Total = </span><small>{this.state.total}</small>
                                    </div>
                                    <button
                                        onClick={this.props.context.clearCart}
                                        className="button is-warning"
                                    >
                                        Clear cart
                                    </button>
                                    {""}
                                    <button
                                        className="button is-success"
                                        onClick={this.props.context.checkout}
                                    >
                                        Checkout
                                    </button>
                                    <Stripe
                                        stripeKey="" // Replace with your Stripe Publishable key
                                        token={this.handleToken}
                                        amount={this.state.total} // Replace with the actual amount to charge
                                        name="Payment Methods"
                                        billingAddress
                                        zipCode
                                        >
                                        <button className="button is-primary is-rounded is-pulled-right">
                                            Pay with Stripe
                                        </button>
                                        </Stripe>
                                </div>
                            </div>
                        </div>
                    ) : (
                        <div className="column">
                            <div className="title has-text-grey-light">No item in cart!</div>
                        </div>
                    )}
                </div>
            </>
        );
    };
}


export default withContext(ShoppingCart);
