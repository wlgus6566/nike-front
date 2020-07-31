import axios from 'axios';
import {setInterceptors} from '@/api/config/interceptors';

const apiCustomer = axios.create({ baseURL: '/api/customer/', timeout: 3000 });
setInterceptors(apiCustomer);

//목록 조회
function getCustomerList(sectionCode, params) {
    return apiCustomer.get(`/${sectionCode}`, {
        params: params,
    });
}

export { getCustomerList };
