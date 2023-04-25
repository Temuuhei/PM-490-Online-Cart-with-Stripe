import React, { Component } from "react";
import withContext from "../withContext";
import axios from "axios";

class Finance extends Component {
  constructor(props) {
    super(props);
    this.state = {
      fromCustomer: 0,
      fromVendor: 0,
      receivable: 0,
      payable: 0,
      tax: 0,
      netIncome: 0,
      loading: true,
    };
  }

    componentDidMount() {
        const { user } = this.props.context;

        if (user) {
            const customer = axios
            .get("http://localhost:8080/api/finance/totalRecAmountByUserRole/CUSTOMER", {
                headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + user.token,
                },
            })
            .then((response) => {
                const fromCustomer = response.data;
                const payable = fromCustomer * 0.93 * 0.8;
                const tax = fromCustomer * 0.07;
                this.setState({ fromCustomer, payable, tax });
            })
            .catch((error) => console.log(error));

            const vendor = axios
            .get("http://localhost:8080/api/finance/totalRecAmountByUserRole/VENDOR", {
                headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + user.token,
                },
            })
            .then((response) => {
                const fromVendor = response.data;
                const receivable = this.state.fromCustomer + fromVendor;
                const netIncome = this.state.fromCustomer + fromVendor - this.state.payable - this.state.tax;
                this.setState({ fromVendor, receivable, netIncome, loading: false });
            })
            .catch((error) => console.log(error));
            }
        }

        downloadTransactionReport = () => {
            const { user } = this.props.context;
            const res = axios({
            url: "http://localhost:8080/api/transaction/report/pdf",
            method: "GET",
            responseType: "blob",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + user.token,
            },
            }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement("a");
            alert('Your report is successfully created in Download - Path is ' + "/Users/temuujintsogt/Downloads/Report/");
            link.href = url;
            link.setAttribute("download", "transactionReport.pdf");
            document.body.appendChild(link);
            link.click();
            });
        };

        downloadProductReport = () => {
            const { user } = this.props.context;
            const res = axios({
            url: "http://localhost:8080/api/product/report/pdf",
            method: "GET",
            responseType: "blob",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + user.token,
            },
            }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement("a");
            alert('Your report is successfully created in Download - Path is ' + "/Users/temuujintsogt/Downloads/Report/");
            link.href = url;
            link.setAttribute("download", "vendor_sales_report.pdf");
            document.body.appendChild(link);
            link.click();
            });
        };

  render() {
    const { fromCustomer, fromVendor, receivable, payable, tax, netIncome, loading } = this.state;
        return (
            <>
                <div className="hero is-info">
                    <div className="hero-body container">
                        <h4 className="title">Finance</h4>
                    </div>
                </div>
                <br/>
                <div className="container">
                    <div>
                        <div>Income from Customers: ${fromCustomer.toFixed(2)}</div>
                        <div>Income from Vendors: ${fromVendor.toFixed(2)}</div>
                        <div>Receivable Account: ${receivable.toFixed(2)}</div>
                        <div>Payable Account: ${payable.toFixed(2)}</div>
                        <div>Purchase Tax Amount: ${tax.toFixed(2)}</div>
                        <div>GL (AR - AP): ${netIncome.toFixed(2)}</div>
                        <br/>
                        <button className="button is-success" onClick={this.downloadTransactionReport}>Download Transaction Report</button>
                        <span>  </span>
                        <button className="button is-success" onClick={this.downloadProductReport}>Download Product Report</button>
                    </div>
                </div>
            </>
        );
    }
}

export default withContext(Finance);
