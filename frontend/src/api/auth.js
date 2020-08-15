import { auth } from './index';

/**
 * 그룹(권한) 관리 목록 조회
 * @returns {Promise<AxiosResponse<any>>}
 */
function getAuthList() {
    return auth.get(`/`);
}
function postAuth(data) {
    return auth.post(`/`, data);
}
function getAuthCacheList() {
    return auth.get(`/list`);
}
function getAuthView(seq) {
    return auth.get(`${seq}`);
}
function delAuth(seq) {
    return auth.delete(`${seq}`);
}

export { getAuthList, getAuthCacheList, getAuthView, delAuth, postAuth };
