import { myOrder } from './index';

// MYPAGE 주문내역
function getMyOrder(params) {
    return myOrder.get(`/list`, {
        params: params,
    });
}
// MYPAGE 주문 상세 내역
function getMyOrderDetail(orderGoodsSeq) {
    return myOrder.get(`/${orderGoodsSeq}`);
}

//MYPAGE 주문내역 등록
function postOrderSave(data) {
    return myOrder.post(`/save`, data);
}

export { getMyOrder, getMyOrderDetail, postOrderSave };
