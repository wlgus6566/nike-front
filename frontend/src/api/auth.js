import { auth } from './index';

// users
function loginUser(data) {
    return auth.get('login', data);
}

export { loginUser };
