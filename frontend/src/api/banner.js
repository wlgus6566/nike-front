import { banner } from './index';

//배너상세
function postBanner(data) {
    return banner.post(`/`, data);
}

//배너 등록
function getBanner(data) {
    return banner.get(`/`, data);
}

//배너수정
function putBanner(bannerSeq, data) {
    return banner.put(`/${bannerSeq}`, data);
}

export { postBanner, getBanner, putBanner };
