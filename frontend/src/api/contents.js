import { contents } from './index';

function getContents(topMenuCode, menuCode, params) {
    return contents.get(`/${topMenuCode}/${menuCode}`, {
        params: params,
    });
}
function getContentsView(topMenuCode, menuCode, contentsSeq) {
    return contents.get(`/${topMenuCode}/${menuCode}/${contentsSeq}`);
}
function postContents(topMenuCode, menuCode, data) {
    return contents.post(`/${topMenuCode}/${menuCode}`, data);
}
function putContents(topMenuCode, menuCode, data) {
    return contents.post(`/${topMenuCode}/${menuCode}`, data);
}
function deleteContents(topMenuCode, menuCode, contentsSeq) {
    return contents.delete(`/${topMenuCode}/${menuCode}/${contentsSeq}`);
}
export { getContents, getContentsView, postContents, putContents, deleteContents };
