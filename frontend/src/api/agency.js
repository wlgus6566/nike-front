import { agency } from './index';

//AGENCY 에이전시 목록 조회
function getAgencyContact() {
    return agency.get(``);
}
//AGENCY 에이전시 등록
function postAgencyContact(data) {
    return agency.post(``, data);
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
