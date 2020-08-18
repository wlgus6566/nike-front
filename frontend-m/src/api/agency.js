import { agency } from './index';

//AGENCY 에이전시 목록 조회
function getAgencyContact(params) {
    return agency.get(`/`, {
        params: params,
    });
}

export {
    getAgencyContact
};
