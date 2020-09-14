import axios from 'axios';

const apiLogin = axios.create({
    baseURL: process.env.VUE_APP_API_URL + '/api',
    timeout: 30000,
});

function loginUser(data) {
    return apiLogin.post('/login', data);
}

function setpawd(data) {
    return apiLogin.put('/open/login/set/pawd', data);
}

function certCode(data) {
    return apiLogin.post('/open/login/check/cert', data);
}

function changepawd(data) {
    return apiLogin.put('/open/login/change/pawd', data);
}

function sendEmail(params) {
    return apiLogin.get('/open/login/send/cert', {
        params: params,
    });
}

export { loginUser, sendEmail, setpawd, changepawd, certCode };
