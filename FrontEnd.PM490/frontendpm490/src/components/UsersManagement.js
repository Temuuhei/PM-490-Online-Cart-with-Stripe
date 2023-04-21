
import React, {Component} from "react";
import withContext from "../withContext";
import axios from 'axios';

const initState = {
    userlogged: null,
    userHandle: null,
    listUsers: [],
};

class UsersManagement extends Component {
    constructor(props) {
        super(props);
        this.state = initState;
    }

    async componentDidMount() {
        const {user} = this.props.context;

        const listUsers = await axios.get('http://localhost:8080/api/user/users',
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + user.token
                }
            }
        );
        this.setState({
            listUsers: listUsers.data
        })
    }

    deleteUser = async (id) => {
        const {user} = this.props.context;
        const res = await axios.delete(
            'http://localhost:8080/api/user/user/' + id,
            {
                headers : {
                    'Authorization': 'Bearer ' + user.token
                }
            }
        ).then(response => {
            console.log('Delete done')
            return response.data;
        }).catch(error => {
            console.log(error.message);
            return {status: 401, message: error}
        })
        if (res.status === 200) {
            alert('Data deleted');
            this.componentDidMount();
        }
    }
    

    editUser = async (id, user) => {
        const {username, password, fullname, phone, email, address, role, status} = user;
        const {context} = this.props;
        const {user: {token}} = context;
    
        try {
            const res = await axios.put(
                `http://localhost:8080/api/user/user/${id}`,
                {username, password, fullname, phone, email, address, role, status},
                {headers: {'Authorization': 'Bearer ' + token}}
            );
    
            if (res.status === 200) {
                alert('User updated successfully!');
                this.componentDidMount();
            }
        } catch (error) {
            console.log(error.message);
            alert('Failed to update user!');
        }
    }
    
    
    addUser = async (e) => {
        e.preventDefault();
        const {username, password, fullname, phone, email, address, role, status} = this.state;
        if (username && password && phone && email && role) {
            const {user} = this.props.context;
            const res = await axios.post(
                'http://localhost:8080/api/user/user',
                {username, password, fullname, phone, email, address, role, status: "ACTIVE"},
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + user.token
                    }
                },
            ).catch((res) => {
                return {status: 401, message: 'Unauthorized'}
            })
            if (res.status == 200) {
                alert('Data Inserted');
                this.componentDidMount();

                // Clear input fields
                this.setState({
                    username: '',
                    password: '',
                    fullname: '',
                    phone: '',
                    email: '',
                    address: '',
                    role: '',
                    status: '',
                });
                this.componentDidMount();
            }

        }
    }

    handleChange = e => this.setState({[e.target.name]: e.target.value, error: ""});

    render() {
        const {username, password, fullname, phone, email, address, role, status} = this.state;
        return (
            <>
                <div className="hero is-info ">
                    <div className="hero-body container">
                        <h4 className="title">Users List</h4>
                    </div>
                </div>
                <br/>
                <br/>
                <div className="container is-widescreen">
                    <form>

                        <div class="field is-grouped is-grouped-centered">
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
                                    <option value="CLIENT">Client</option>
                                </select>
                            </div>

                            {this.state.flash && (
                                <div className={`notification ${this.state.flash.status}`}>
                                    {this.state.flash.msg}
                                </div>
                            )}


                        </div>
                        <div className="field is-clearfix">
                            <button
                                className="button is-primary is-outlined is-pulled-right"
                                type="submit" onClick={this.addUser}
                            >
                                Submit
                            </button>
                        </div>
                    </form>

                    <br></br>
                    <div className="row">
                        <table class="table is-bordered ">
                            <thead>
                            <tr>
                                <th> ID</th>
                                <th> Username</th>
                                <th> Full Name</th>
                                <th> Phone</th>
                                <th> Email</th>
                                <th> Address</th>
                                <th> Role</th>
                                <th> Status</th>
                                <th> Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                this.state.listUsers.map(
                                    user =>
                                        <tr key={user.id}>
                                            <td> {user.id}</td>
                                            <td> {user.username} </td>
                                            <td> {user.fullname}</td>
                                            <td> {user.phone}</td>
                                            <td> {user.email}</td>
                                            <td> {user.address}</td>
                                            <td> {user.role}</td>
                                            <td> {user.status}</td>
                                            <td>
                                                <button onClick={() => this.editUser(user.id, user)}
                                                        className="btn btn-info">Update
                                                </button>
                                                <button style={{marginLeft: "10px"}}
                                                        onClick={() => this.deleteUser(user.id)}
                                                        className="btn btn-danger">Delete
                                                </button>

                                            </td>
                                        </tr>
                                )
                            }
                            </tbody>
                        </table>

                    </div>

                </div>
            </>
        )
    }
}

export default withContext(UsersManagement);
