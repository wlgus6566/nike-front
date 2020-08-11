import { myPage } from './index';

// MYPAGE MY INFO 상세 조회
function getMyInfo(params) {
    return myPage.get(`/user`, {
        params: params,
    });
}
// MYPAGE MY INFO 비밀번호 변경
function changePassword(data) {
    return myPage.put(`/user/change/password`, data);
    //console.log(params);
}
export { getMyInfo, changePassword };
