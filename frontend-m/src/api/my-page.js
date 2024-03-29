import { myPage } from './index';

// menu 상세 조회
function getGnbMenu() {
    return myPage.get(`/user/gnb`);
}

// MYPAGE MY INFO 상세 조회
function getMyInfo(params) {
    return myPage.get(`/user`, {
        params: params,
    });
}
// MYPAGE MY INFO 비밀번호 변경
function changepassword(data) {
    return myPage.put(`/user/change/password`, data);
}

export { getGnbMenu, getMyInfo, changepassword };
