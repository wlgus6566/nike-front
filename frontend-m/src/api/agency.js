import axios from 'axios';
import { setInterceptors } from '@/api/config/interceptors';

const agency = axios.create({ baseURL: '/api/agency', timeout: 3000 });
setInterceptors(agency);

//AGENCY 에이전시 목록 조회
function getAgencyContact(params) {
    return agency.get(`/`, {
        params: params,
    });
}
//AGENCY 에이전시 등록
function postAgencyContact(data) {
    return agency.post(`/`, data);
}
//AGENCY 에이전시 삭제
function delAgencyContact(agencySeq) {
    return agency.delete(`/${agencySeq}`);
}
//AGENCY 에이전시 상세조회
function getDetailAgencyContact(agencySeq) {
    return agency.get(`/${agencySeq}`);
}
//AGENCY 에이전시 수정
function putAgencyContact(agencySeq, data) {
    return agency.put(`/${agencySeq}`, data);
}
export {
    getAgencyContact,
    postAgencyContact,
    delAgencyContact,
    getDetailAgencyContact,
    putAgencyContact,
};
