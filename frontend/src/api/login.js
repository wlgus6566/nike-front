import axios from 'axios';

const apiLogin = axios.create({ baseURL: '/api', timeout: 3000 });

function loginUser(data) {
    return apiLogin.post('/login', data);
}

function setPassword(data) {
    return apiLogin.put('/open/login/set/password', data);
}
function certCode(data) {
    return apiLogin.post('/open/login/check/cert', data);
}

function changePassword(data) {
    return apiLogin.put('/open/login/change/password', data);
}

function sendEmail(params) {
    return apiLogin.get('/open/login/send/cert', {
        params: params,
    });
}

export { loginUser, sendEmail, setPassword, changePassword, certCode };
