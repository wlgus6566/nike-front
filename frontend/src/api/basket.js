import axios from 'axios';
import { setInterceptors } from '@/api/config/interceptors';

const apiBasket = axios.create({ baseURL: '/api/goodsBasket', timeout: 3000 });
setInterceptors(apiBasket);

// 장바구니 리스트
function getBasketList(params) {
    return apiBasket.get(`/list`, {
        params: params,
    });
}
// 장바구니 등록 및 수정
function postBasketSave(data) {
    return apiBasket.post(`/save`, data);
}
// 장바구니 다건 등록 및 수정
function postBasketSaveList(data) {
    return apiBasket.post(`/saveList`, data);
}
// 장바구니 삭제
function deleteBasket(goodsBasketSeq) {
    return apiBasket.delete(`/${goodsBasketSeq}`);
}

export { getBasketList, postBasketSave, postBasketSaveList, deleteBasket };
