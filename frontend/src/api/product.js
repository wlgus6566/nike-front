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

// PRODUCT 목록 유저 조회
function getUserProductList(category2Code, params) {
    return apiProduct.get(`/${category2Code}/list`, {
        params: params,
    });
}
// PRODUCT 상세 조회
function getProductDetail(goodsSeq, params) {
    return apiProduct.get(`/list/${goodsSeq}`, {
        params: params,
    });
}
// PRODUCT 상품 삭제[배열]
function delProduct(params) {
    return apiProduct.delete(`/`, {
        params: params,
    });
}
export { getProductList, getUserProductList, getProductDetail, delProduct };
