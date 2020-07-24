import axios from 'axios';
import {setInterceptors} from '@/api/config/interceptors';

const apiMyPage = axios.create({ baseURL: '/api/mypage', timeout: 3000 });
setInterceptors(apiMyPage);
function getMyInfo(params) {
    return apiMyPage.get(`/`, {
        params: params,
    });
}
function changePassword(data) {
    return apiMyPage.put(`/change/password`, data);
    //console.log(params);
}
export { getMyInfo, changePassword };
