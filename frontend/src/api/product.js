import { product, order } from './index';

// PRODUCT 목록 조회
function getProductList(params) {
    return product.get(`/list`, {
        params: params,
    });
}

// PRODUCT 목록 유저 조회
function getUserProductList(category2Code, params) {
    return product.get(`/${category2Code}/list`, {
        params: params,
    });
}
// PRODUCT 상세 조회
function getProductDetail(goodsSeq, params) {
    return product.get(`/${goodsSeq}`, {
        params: params,
    });
}

// PRODUCT 등록
function postProduct(data) {
    //console.log(data);
    return product.post(``, data);
}

// PRODUCT 수정
function putProduct(goodsSeq, data) {
    //console.log(data);
    return product.put(`/${goodsSeq}`, data);
}

// PRODUCT 상품 삭제[배열]
function delProduct(data) {
    return product.delete(``, { data });
}

// 수산자 불러오기
function recipientList() {
    return order.get(`/recipientList`);
}
export {
    getProductList,
    getUserProductList,
    getProductDetail,
    postProduct,
    putProduct,
    delProduct,
    recipientList,
};
