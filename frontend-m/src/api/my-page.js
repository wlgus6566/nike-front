import {myPage} from './index';

// menu 상세 조회
function getGnbList(params) {
    console.log(params);
    return myPage.get(`/user/gnb`, {
        params: params,
    });
}

// MYPAGE MY INFO 상세 조회
function getMyInfo(params) {
    return myPage.get(`/user`, {
        params: params,
    });
}
// MYPAGE MY INFO 비밀번호 변경
function changePassword(data) {
    return myPage.put(`/user/change/password`, data);
}

export {
    getGnbList,
    getMyInfo,
    changePassword
};
