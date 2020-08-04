import axios from 'axios';
import {setInterceptors} from '@/api/config/interceptors';

const apiMyPage = axios.create({ baseURL: '/api/mypage', timeout: 3000 });
setInterceptors(apiMyPage);

// MYPAGE 최근 본 폴더 목록
function historyFolderViewList(params) {
    //console.log(data);
    return apiMyPage.get(`/history/viewList`, {
        params: params,
    });
}

// MYPAGE MY INFO 상세 조회
function getMyInfo(params) {
    return apiMyPage.get(`/user`, {
        params: params,
    });
}
// MYPAGE MY INFO 비밀번호 변경
function changePassword(data) {
    return apiMyPage.put(`/user/change/password`, data);
    //console.log(params);
}
export { historyFolderViewList, getMyInfo, changePassword };
