import axios from 'axios';
import { setInterceptors } from '@/api/config/interceptors';

const apiCustomer = axios.create({ baseURL: '/api/customer/', timeout: 3000 });
setInterceptors(apiCustomer);

//목록 조회
function getCustomerList(sectionCode, params) {
    //console.log(params);
    return apiCustomer.get(`/${sectionCode}`, {
        params: params,
    });
}

//공지사항 상세
function getCustomerDetail(noticeSeq) {
    return apiCustomer.get(`/detail/${noticeSeq}`);
}

//공지사항 등록
function postCustomerNotice(params) {
    return apiCustomer.post(`/notice`, {
        params: params,
    });
}

//공지사항 수정
// function putNotice(noticeSeq, data) {
//     return apiCustomer.put(`/notice/${noticeSeq}`, data);
// }

export { getCustomerList, getCustomerDetail, postCustomerNotice };
