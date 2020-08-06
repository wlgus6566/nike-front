import {user} from './index';

// user 목록 조회
function getUser(params) {
    return user.get(`/`, {
        params: params,
    });
}

export { getUser };
