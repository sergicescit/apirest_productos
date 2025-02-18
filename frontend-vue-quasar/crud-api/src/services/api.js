import axios from 'axios';

const username = 'usuario';  //conexió workbench8 local
const password = '1234';  //conexió workbench8 local

export const apiCliente = axios.create({
    baseURL: 'http://localhost:8081',
    auth: {
        username: username,
        password: password,
    },
    withCredentials: true,
    });
