import React, { useState } from 'react';
import axios from 'axios';
import {Button} from "@mantine/core";


const AuthorisationPage= (): React.JSX.Element => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async (event: React.FormEvent) => {
        event.preventDefault();

        const token = btoa(`${username}:${password}`);
        const config = {
            headers: {
                'Authorization': `Basic ${token}`
            }
        };

        try {
            // const response = await axios.post('http://localhost:8080/api/cat', {}, config);
            const response = await axios.get('http://localhost:8080/api/cat', config);
            console.log('Login successful:', response.data);
        } catch (error) {
            console.error('Login failed:', error);
        }
    };

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <div>
                    <label>Username:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default AuthorisationPage;
