import axios from 'axios';
import {setInterceptors} from '@/api/config/interceptors';

const apiAgency = axios.create({ baseURL: '/api/agency', timeout: 3000 });
setInterceptors(apiAgency);

function getAgencyContact(params) {
    return apiAgency.get(`/`, {
        params: params,
    });
}
function postAgencyContact(data) {
    return apiAgency.post(`/`, data);
}
function delAgencyContact(agencySeq) {
    return apiAgency.delete(`/${agencySeq}`);
}
function getDetailAgencyContact(agencySeq) {
    return apiAgency.get(`/${agencySeq}`);
}
function putAgencyContact(agencySeq, data) {
    return apiAgency.put(`/${agencySeq}`, data);
}
export {
    getAgencyContact,
    postAgencyContact,
    delAgencyContact,
    getDetailAgencyContact,
    putAgencyContact,
};
