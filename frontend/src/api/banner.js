import { banner } from './index';

//배너상세
function postBanner(data) {
    return banner.post(`/`, data);
}

//배너상세
function getBanner(data) {
    return banner.get(`/`, data);
}

export { postBanner, getBanner };
