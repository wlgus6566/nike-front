import { customer } from './index';

//목록 조회
function getCustomerList(sectionCode, params) {
    return customer.get(`/${sectionCode}`, {
        params: params,
    });
}

export { getCustomerList };
