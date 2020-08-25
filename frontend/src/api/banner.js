import { banner } from './index';

//배너등록
function postBanner(data) {
    return banner.post(`/`, data);
}

//배너 상세
function getBanner(data) {
    return banner.get(`/`, data);
}

//배너수정
function putBanner(seq, data) {
    return banner.put(`/${seq}`, data);
}

export { postBanner, getBanner, putBanner };
