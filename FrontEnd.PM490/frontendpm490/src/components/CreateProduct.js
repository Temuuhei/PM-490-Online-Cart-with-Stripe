
import React, { Component } from "react";
import withContext from "../withContext";
import { Redirect } from "react-router-dom";
import axios from 'axios';

const initState = {
    id:0,
    name: "",
    color: "",
    idVendor: 0,
    status: "",

    quantity:0,
    idCategory:0,
    price:0,

    listCategory: [],

};

class CreateProduct extends Component {
    constructor(props) {
        super(props);
        this.state = initState;

    }
 async componentDidMount(){
     const listCategory = await axios.get('http://localhost:8080/api/category/');
     this.setState({
         listCategory: listCategory.data
     })
 }
    handleChange = e => this.setState({[e.target.name]: e.target.value, error: ""});
    save = async (e) => {
        e.preventDefault();
        const { name, color, idVendor,status,quantity, idCategory, price } = this.state;

        if (name && price) {
            //const id = Math.random().toString(36).substring(2) + Date.now().toString(36);
            const { user } = this.props.context;
            await axios.post(
                'http://localhost:8080/api/product/saveproduct',
                { name, color, idVendor: user.id, status: "APPROVED",quantity, idCategory, price },
                {headers: {'Content-Type': 'application/json',
                        'Authorization': 'Bearer '+ user.token}
            },

        )

            this.props.context.createProduct(
                {
                    name,
                    color,
                    idVendor,
                    status,
                    quantity,
                    idCategory,
                    price
                },
                () => {
                    this.setState(initState);
                    this.props.history.push('/products');
                }
            );
            this.setState({
                flash: { status: 'is-success', msg: 'Product created successfully' }
            }, () => {
                this.props.history.push('/products');
            });

        } else {
            this.setState(
                { flash: { status: 'is-danger', msg: 'Please enter name and price' }}
            );
        }
    };

    render() {

        const { name, color,quantity, idCategory, price } = this.state;
        const { user } = this.props.context;
        console.log("###################"+user.token);
        console.log("----"+user.username);

        return !(user && user.role === "VENDOR" ) ? (
            <Redirect to="/" />
        ) : (
            <>
                <div className="hero is-info ">
                    <div className="hero-body container">
                        <h4 className="title">Create Product</h4>
                    </div>
                </div>
                <br />
                <br />
                <form onSubmit={this.save}>
                    <div className="columns is-mobile is-centered">
                        <div className="column is-one-third">
                            <div className="field">
                                <label className="label">Product Name: </label>
                                <input
                                    className="input"
                                    type="text"
                                    name="name"
                                    value={name}
                                    onChange={this.handleChange}
                                    required
                                />
                            </div>
                            <div className="field">
                                <label className="label">color: </label>
                                <input
                                    className="input"
                                    type="text"
                                    name="color"
                                    value={color}
                                    onChange={this.handleChange}
                                    required
                                />
                            </div>
                            <div className="field">
                                <label className="label">quantity: </label>
                                <input
                                    className="input"
                                    type="number"
                                    name="quantity"
                                    value={quantity}
                                    onChange={this.handleChange}
                                />
                            </div>
                            <div className="field">
                                <label className="label">category: </label>

                               <select
                                    name="idCategory"
                                    className="input"
                                    onChange={this.handleChange}
                                    value={idCategory}>
                               <option value="">Category</option>
                                   {this.state.listCategory.map(cat => <option value={cat.id}>{cat.name}</option>)}
                               </select>
                            </div>
                            <div className="field">
                                <label className="label">price: </label>
                                <input
                                    className="input"
                                    type="number"
                                    name="price"
                                    value={price}
                                    onChange={this.handleChange}
                                />
                            </div>
                            
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

export default withContext(CreateProduct);
