import { myOrder } from './index';

// MYPAGE 주문내역
function getMyOrder(params) {
    return myOrder.get(`/mypage/order/list`, {
        params: params,
    });
}
// MYPAGE 주문 상세 내역
function getMyOrderDetail(orderSeq) {
    return myOrder.get(`/mypage/order/${orderSeq}`);
}

//MYPAGE 주문내역 등록
function postOrderSave(data) {
    return myOrder.post(`/order/save/2nd`, data);
}

export { getMyOrder, getMyOrderDetail, postOrderSave };
