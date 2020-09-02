import axios from 'axios';
import { setInterceptors } from '@/api/config/interceptors';

const apiCustomer = axios.create({ baseURL: process.env.VUE_APP_API_URL + '/api/customer/', timeout: 3000 });
setInterceptors(apiCustomer);

//목록 조회 공통
function getCustomerList(sectionCode, params) {
    console.log("getCustomerList : " + params);
    return apiCustomer.get(`/${sectionCode}`, {
        params: params,
    });
}

//상세 공통
function getCustomerDetail(noticeArticleSectionCode, noticeSeq) {
    return apiCustomer.get(`/${noticeArticleSectionCode}/${noticeSeq}`);
}

export {
    getCustomerList,
    getCustomerDetail
};
