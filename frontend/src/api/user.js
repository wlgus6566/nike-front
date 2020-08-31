import { user } from './index';

// user 목록 조회
function getUser(params) {
    return user.get(``, {
        params: params,
    });
}
// user 추가
function postUser(data) {
    return user.post(``, data);
}

// user 수정
function putUser(userSeq, data) {
    return user.put(`/${userSeq}`, data);
}

// user id 중복체크
function getDuplicate(params) {
    return user.get(`/duplicate`, {
        params: params,
    });
}

// user 상세조회
function getUserDetail(userSeq, params) {
    return user.get(`/${userSeq}`, {
        params: params,
    });
}

// user 배열 삭제
function deleteArrayUser(data) {
    //console.log(data);
    return user.delete(``, { data });
}

// user 단건 삭제
function deleteUser(userSeq, params) {
    return user.delete(`/${userSeq}`, {
        params: params,
    });
}

export {
    getUser,
    postUser,
    putUser,
    getDuplicate,
    getUserDetail,
    deleteArrayUser,
    deleteUser,
};
