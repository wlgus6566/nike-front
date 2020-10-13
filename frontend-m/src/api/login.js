import axios from 'axios';

const apiLogin = axios.create({
    baseURL: process.env.VUE_APP_API_URL + '/api',
    timeout: 30000,
});

function loginUser(data) {
    return apiLogin.post('/login', data);
}

function setpassword(data) {
    return apiLogin.put('/open/login/set/password', data);
}

function certCode(data) {
    return apiLogin.post('/open/login/check/cert', data);
}

function changepassword(data) {
    return apiLogin.put('/open/login/change/password', data);
}

function sendEmail(params) {
    return apiLogin.get('/open/login/send/cert', {
        params: params,
    });
}
function sendCode(params) {
    return apiLogin.get('/open/login/send/cert/code', {
        params: params,
    });
}

export {
    loginUser,
    sendEmail,
    setpassword,
    changepassword,
    certCode,
    sendCode,
};
