import axios from 'axios';
import { setInterceptors } from '@/api/config/interceptors';

const basket = axios.create({ baseURL: '/api/goodsBasket', timeout: 3000 });
setInterceptors(basket);

// 장바구니 리스트
function getBasketList(params) {
    return basket.get(`/list`, {
        params: params,
    });
}
// 장바구니 등록 및 수정
function postBasketSave(data) {
    return basket.post(`/save`, data);
}
// 장바구니 다건 등록 및 수정
function postBasketSaveList(data) {
    return basket.post(`/saveList`, data);
}
// 장바구니 삭제
function deleteBasket(goodsBasketSeq) {
    return basket.delete(`/${goodsBasketSeq}`);
}

export { getBasketList, postBasketSave, postBasketSaveList, deleteBasket };
