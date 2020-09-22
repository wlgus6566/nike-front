import { contents } from './index';

// 컨텐츠 목록 조회
function getContents(topMenuCode, menuCode, params) {
    return contents.get(`/${topMenuCode}/${menuCode}`, {
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
    return contents.get(`/${topMenuCode}/${menuCode}/${contentsSeq}`);
}

function getContentsViewFile(topMenuCode, menuCode, contentsSeq, params) {
    return contents.get(`/${topMenuCode}/${menuCode}/${contentsSeq}/file`, {
        params: params,
    });
}

function postContents(topMenuCode, menuCode, data) {
    return contents.post(`/${topMenuCode}/${menuCode}`, data);
}
function putContents(topMenuCode, menuCode, id, data) {
    return contents.put(`/${topMenuCode}/${menuCode}/${id}`, data);
}
function deleteContents(topMenuCode, menuCode, contentsSeq) {
    return contents.delete(`/${topMenuCode}/${menuCode}/${contentsSeq}`);
}

// 컨텐츠 장바구니 목록 조회
function getContentsBasket() {
    return contents.get('/basket');
}
// 컨텐츠 장바구니 등록
function addContentsBasket(topMenuCode, menuCode, data) {
    return contents.post(`/basket/${topMenuCode}/${menuCode}`, data);
}
// 컨텐츠 장바구니 삭제
function delContentsBasket(contentsBasketSeq) {
    return contents.delete(`/basket/${contentsBasketSeq}`);
}

// 컨텐츠 권한 목록 조회
function getContentsAuthList(topMenuCode, menuCode, params) {
    return contents.get(`/${topMenuCode}/${menuCode}/authList`, {
        params: params,
    });
}

// 컨텐츠 권한 목록 조회
function sendMail(data) {
    return contents.post(`/sendMail`, data);
}

// 컨텐츠 파일 다운로드
/*function contentFileDownload(seq, config) {
    return contents.get(`/download/${seq}`, config);
}*/

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
    getContentsAuthList,
    sendMail,
    //contentFileDownload,
};
