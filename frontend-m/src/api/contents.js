import { apiContents } from './index';

/**
 * 컨텐츠 목록 조회
 * @param topMenuCode
 * @param menuCode
 * @param params
 * @returns {Promise<AxiosResponse<any>>}
 */
function getContents(topMenuCode, menuCode, params) {
    return apiContents.get(`/${topMenuCode}/${menuCode}/`, {
        params: params,
    });
}

/**
 * @description 컨텐츠 상세 조회
 * @param {string} topMenuCode (ASSET,TOOLKIT,FOUNDATION)
 * @param {string} menuCode
 * @param {number} contentsSeq 컨텐츠 시컨스
 */
function getContentsView(topMenuCode, menuCode, contentsSeq) {
    return apiContents.get(`/${topMenuCode}/${menuCode}/${contentsSeq}`);
}
function getContentsViewFile(topMenuCode, menuCode, contentsSeq, params) {
    return apiContents.get(`/${topMenuCode}/${menuCode}/${contentsSeq}/file`, {
        params: params,
    });
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

// 컨텐츠 장바구니 목록 조회
function getContentsBasket() {
    return apiContents.get('/basket/');
}
// 컨텐츠 장바구니 등록
function addContentsBasket(topMenuCode, menuCode, data) {
    return apiContents.post(`/basket/${topMenuCode}/${menuCode}`, data);
}
// 컨텐츠 장바구니 삭제
function delContentsBasket(contentsBasketSeq) {
    return apiContents.delete(`/basket/${contentsBasketSeq}`);
}

export {
    getContents,
    getContentsView,
    getContentsViewFile,
    postContents,
    putContents,
    deleteContents,
    getContentsBasket,
    addContentsBasket,
    delContentsBasket,
};
