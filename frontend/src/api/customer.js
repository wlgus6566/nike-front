import axios from 'axios';
import { setInterceptors } from '@/api/config/interceptors';

const apiCustomer = axios.create({ baseURL: '/api/customer/', timeout: 3000 });
setInterceptors(apiCustomer);

//목록 조회 공통
function getCustomerList(sectionCode, params) {
    //console.log(params);
    return apiCustomer.get(`/${sectionCode}`, {
        params: params,
    });
}

//상세 공통
function getCustomerDetail(noticeSeq) {
    return apiCustomer.get(`/detail/${noticeSeq}`);
}

//삭제 공통
function deleteCustomer(noticeSeq) {
    return apiCustomer.delete(`/${noticeSeq}`);
}

//공지사항 등록
function postNotice(data) {
    return apiCustomer.post(`/NOTICE`, data);
}

//공지사항 수정
function putNotice(noticeSeq, data) {
    return apiCustomer.put(`/NOTICE/${noticeSeq}`, data);
}

//뉴스 등록
function postNews(data) {
    return apiCustomer.post(`/NEWS`, data);
}

//뉴스 수정
function putNews(noticeSeq, data) {
    return apiCustomer.put(`/NEWS/${noticeSeq}`, data);
}

//FAQ 등록
function postFaq(data) {
    return apiCustomer.post(`/QnA`, data);
}

//FAQ 수정
function putFaq(noticeSeq, data) {
    return apiCustomer.put(`/QNA/${noticeSeq}`, data);
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
