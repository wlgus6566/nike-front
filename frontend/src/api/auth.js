import { auth } from './index';

// users
function loginUser(data) {
    return auth.get('/api/user', data);
}

function signupUser(data) {
    return auth.post('/api/user', data);
}

export { loginUser, signupUser };
