import { banner } from './index';

//배너등록
function postBanner(data) {
    return banner.post(`/`, data);
}

export { postBanner };
