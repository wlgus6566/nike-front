import {myPage} from './index';

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

// login 연장
function getLoginUpdate() {
    return myPage.get(`/user/init`);
}

export {
    uploadFolderViewList,
    historyFolderViewList,
    getLoginUpdate
};
