import { user } from './index';

// user 목록 조회
function getUser(params) {
    return user.get(`/`, {
        params: params,
    });
}

// user 추가
function postUser(data) {
    return user.post(`/`, data);
}

// user id 중복체크
function getDuplicate(params) {
    return user.get(`/duplicate`, {
        params: params,
    });
}

// user 배열 삭제
function deleteUser(data) {
    return user.delete(`/`, data);
}

export { getUser, postUser, getDuplicate, deleteUser };
