import { contents } from './index';

// 컨텐츠 목록 조회
function getContents(topMenuCode, menuCode, params) {
    return contents.get(`/${topMenuCode}/${menuCode}/`, {
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

function getContentsFileCount(topMenuCode, menuCode, contentsSeq) {
    return contents.get(`/${topMenuCode}/${menuCode}/${contentsSeq}/fileCount`);
}

export {
    getContents,
    getContentsView,
    getContentsViewFile,
    getContentsFileCount
};
