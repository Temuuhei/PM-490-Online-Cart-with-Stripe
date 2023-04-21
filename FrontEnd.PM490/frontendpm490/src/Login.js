
/*
import { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

function Login(){
    const[username,setUsername] = useState("");
    const[password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    let handleSubmit = async (e) => {
        e.preventDefault();
        try {
            let res = await fetch("http://localhost:8080/api/auth/signin", {
                method: "POST",
                headers: {
                    'Content-type': 'application/json; charset=UTF-8'
                },
                body: JSON.stringify({
                    username: username,
                    password: password,
                }),
            });
            let resJson = await res.json();
            if (res.status === 200) {
                setUsername("");
                setPassword("");
                setMessage("User logged in successfully");
            } else {
                setMessage("Some error occurred");
            }
        } catch (err) {
            console.log(err);
        }
    };

    return (
        <div className="Login">
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    value={username}
                    placeholder="UserName"
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    value={password}
                    placeholder="Password"
                    onChange={(e) => setPassword(e.target.value)}
                />

                <button type="submit">Login</button>

                <div className="message">{message ? <p>{message}</p> : null}</div>
            </form>
        </div>
    );
}

export default Login;
*/