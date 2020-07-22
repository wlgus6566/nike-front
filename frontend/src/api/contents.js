import axios from 'axios';
import { setInterceptors } from '@/api/config/interceptors';

const apiContents = axios.create({ baseURL: '/api/contents/' });
setInterceptors(apiContents);

function getContents(topMenuCode, menuCode, params) {
    return apiContents.get(`/${topMenuCode}/${menuCode}`, {
        params: params,
    });
}
function getContentsView(topMenuCode, menuCode, contentsSeq) {
    return apiContents.get(`/${topMenuCode}/${menuCode}/${contentsSeq}`);
}
function postContents(topMenuCode, menuCode, data) {
    return apiContents.post(`/${topMenuCode}/${menuCode}`, data);
}
function putContents(topMenuCode, menuCode, data) {
    return apiContents.post(`/${topMenuCode}/${menuCode}`, data);
}
function deleteContents(topMenuCode, menuCode, contentsSeq) {
    return apiContents.delete(`/${topMenuCode}/${menuCode}/${contentsSeq}`);
}
export { getContents, getContentsView, postContents, putContents, deleteContents };
