import { customer } from './index';

//목록 조회 공통
function getCustomerList(sectionCode, params) {
    //console.log(params);
    return customer.get(`/${sectionCode}`, {
        params: params,
    });
}

//상세 공통
function getCustomerDetail(noticeArticleSectionCode, noticeSeq) {
    return customer.get(`/${noticeArticleSectionCode}/${noticeSeq}`);
}

//삭제 공통
function deleteCustomer(noticeArticleSectionCode, noticeSeq) {
    return customer.delete(`/${noticeArticleSectionCode}/${noticeSeq}`);
}

//공지사항 등록
function postNotice(data) {
    return customer.post(`/NOTICE`, data);
}

//공지사항 수정
function putNotice(noticeSeq, data) {
    return customer.put(`/NOTICE/${noticeSeq}`, data);
}

//뉴스 등록
function postNews(data) {
    return customer.post(`/NEWS`, data);
}

//뉴스 수정
function putNews(noticeSeq, data) {
    return customer.put(`/NEWS/${noticeSeq}`, data);
}

//FAQ 등록
function postFaq(data) {
    return customer.post(`/QNA`, data);
}

//FAQ 수정
function putFaq(noticeSeq, data) {
    return customer.put(`/QNA/${noticeSeq}`, data);
}

export {
    getCustomerList,
    getCustomerDetail,
    postNotice,
    putNotice,
    deleteCustomer,
    postNews,
    putNews,
    postFaq,
    putFaq,
};
