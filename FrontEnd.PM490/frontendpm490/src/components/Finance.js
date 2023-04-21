
import React, {Component} from "react";
import withContext from "../withContext";
import axios from "axios";

const finance = {
    fromCustomer: 0,
    fromVendor: 0,
    receivable: 0,
    payable: 0,
    tax: 0,
    netIncome: 0
};


class Finance extends Component {
    constructor(props) {
        super(props);
        this.state = finance;
    }

    componentDidMount() {
        const {user} = this.props.context;

        const customer = axios.get('http://localhost:8080/api/finance/totalRecAmountByUserRole/CUSTOMER',
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + user.token
                }
            }
        ).then((response) => {
            finance.fromCustomer = response.data;
            finance.payable = response.data * 0.93 * 0.8;
            finance.tax = response.data * 0.07;
        });

        const vendor = axios.get('http://localhost:8080/api/finance/totalRecAmountByUserRole/VENDOR',
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + user.token
                }
            }
        ).then((response) => {
            finance.fromVendor = response.data;
            finance.receivable = finance.fromCustomer + response.data;
            finance.netIncome = finance.fromCustomer + response.data - finance.payable - finance.tax;
            this.setState({finance: finance});
        });

    }

    render() {
        return (
            <>
                <div className="hero is-primary">
                    <div className="hero-body container">
                        <h4 className="title">Finance</h4>
                    </div>
                </div>
                <br/>
                <div className="container">
                    <div>
                        <div>Income from Customers: ${finance.fromCustomer.toFixed(2)}</div>
                        <div>Income from Vendors: ${finance.fromVendor.toFixed(2)}</div>
                        <div>Receivable Account: ${finance.receivable.toFixed(2)}</div>
                        <div>Payable Account: ${finance.payable.toFixed(2)}</div>
                        <div>Purchase Tax Amount: ${finance.tax.toFixed(2)}</div>
                        <div>GL (AR - AP): ${finance.netIncome.toFixed(2)}</div>
                    </div>
                </div>
            </>
        );
    }
}

export default withContext(Finance);
