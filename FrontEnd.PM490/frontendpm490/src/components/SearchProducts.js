
import React, {Component} from "react";
import withContext from "../withContext";
import axios from "axios";

class Search extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            status: "",
            color: "",
            idVendor: 0,
            idCategory: 0,
            listColors: [],
            listVendors:[],
            listCategory:[]
        };
        this.routerRef = React.createRef();
    }

    async componentDidMount() {
        const listColors = await axios.get('http://localhost:8080/api/product/getcolors/');
        const listVendors = await axios.get('http://localhost:8080/api/user/getbyrole/VENDOR');
        const listCategory = await axios.get('http://localhost:8080/api/category/');
        this.setState({
            listColors : listColors.data,
            listVendors : listVendors.data,
            listCategory: listCategory.data
        })
        console.log("listColors : " + listColors);
        console.log("listColors : " + listVendors);
        console.log("listColors : " + listCategory);
    }

    handleChange = e => this.setState({[e.target.name]: e.target.value, error: ""});

    search = (e) => {
        e.preventDefault();
        let {name, status, color, idVendor, idCategory} = this.state;
        if (status === ""){ status= "APPROVED"};
        if (name) {
            this.props.context.search(name, status, color, idVendor, idCategory)
        } else {
            window.location.replace("/");
        }
    };

    render() {
        const { user } = this.props.context;

        return (
            <form onSubmit={this.search}>
                <div class="field has-addons ">
                    <p class="control is-expanded">
                        <input
                            class="input"
                            type="text"
                            name="name"
                            placeholder="Search products"
                            onChange={this.handleChange}
                            value={this.state.search}
                        />
                    </p>
                    {user && user.role === "ADMIN" && (
                    <p className="select">
                        <select name="status"
                                class="input"
                                defaultValue="APPROVED"
                                onChange={this.handleChange}
                                value={this.state.search}>
                            <option selected value="APPROVED">Approved</option>
                            <option value="NOTAPPROVED">Not Approved</option>
                            <option value="DELETED">Deleted</option>
                        </select>
                    </p>
                    )}
                    <p className="select">
                        <select name="color"
                                className="input"
                                onChange={this.handleChange}
                                value={this.state.search}>
                            <option value="">Color</option>
                            {this.state.listColors.map(color => <option value={color}>{color}</option>)}
                        </select>
                    </p>
                    <p className="select">
                        <select name="idVendor"
                                className="input"
                                onChange={this.handleChange}
                                value={this.state.search}>
                            <option value="">Vendor</option>
                            {this.state.listVendors.map(vendor => <option value={vendor.id}>{vendor.username}</option>)}
                        </select>
                    </p>
                    <p className="select">
                        <select name="idCategory"
                                className="input"
                                onChange={this.handleChange}
                                value={this.state.search}>
                            <option value="">Category</option>
                            {this.state.listCategory.map(cat => <option value={cat.id}>{cat.name}</option>)}
                        </select>
                    </p>
                    <p class="control">
                        <button class="button" >
                            🔎
                        </button>
                    </p>
                </div>
            </form>
        );
    }
}

export default withContext(Search);
