import { myPage } from './index';

// gnbMenu
function getGnbMenu() {
    return myPage.get(`/user/gnb`);
}

// MYPAGE 최근 업로드 한 폴더 목록
function uploadFolderViewList(params) {
    return myPage.get(`/history/uploadList`, {
        params: params,
    });
}

// MYPAGE 최근 본 폴더 목록
function historyFolderViewList(params) {
    return myPage.get(`/history/viewList`, {
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
function changepassword(data) {
    return myPage.put(`/user/change/password`, data);
    //console.log(params);
}

// login 연장
function getLoginUpdate() {
    return myPage.get(`/user/init`);
}
export {
    getGnbMenu,
    uploadFolderViewList,
    historyFolderViewList,
    getMyInfo,
    changepassword,
    getLoginUpdate,
};
