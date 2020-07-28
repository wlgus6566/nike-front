import axios from 'axios';
import {setInterceptors} from '@/api/config/interceptors';

const apiProduct = axios.create({ baseURL: '/api/product', timeout: 3000 });
setInterceptors(apiProduct);

// PRODUCT 목록 조회
function getProductList(params) {
    return apiProduct.get(`/list`, {
        params: params,
    });
}
// PRODUCT 상세 조회
function getProductDetail(goodsSeq, params) {
    return apiProduct.get(`/list/${goodsSeq}`, {
        params: params,
    });
}
export { getProductList, getProductDetail };
