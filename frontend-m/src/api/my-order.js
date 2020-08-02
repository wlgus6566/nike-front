import axios from 'axios';
import { setInterceptors } from '@/api/config/interceptors';

const apiMyOrder = axios.create({ baseURL: '/api/order', timeout: 3000 });
setInterceptors(apiMyOrder);

// MYPAGE 주문내역
function getMyOrder(params) {
    return apiMyOrder.get(`/list`, {
        params: params,
    });
}
// MYPAGE 주문 상세 내역
function getMyOrderDetail(orderGoodsSeq) {
    return apiMyOrder.get(`/${orderGoodsSeq}`);
}
export { getMyOrder, getMyOrderDetail };
