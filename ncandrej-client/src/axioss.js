import axios from 'axios';
import https from 'https';

const axioss = axios.create({
    baseURL: 'https://localhost:8102/',
    httpsAgent: new https.Agent({
        rejectUnauthorized: false
    })
});
export default axioss;