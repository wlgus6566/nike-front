import axios from 'axios';
import { setInterceptors } from '@/api/config/interceptors';

const apiBasket = axios.create({ baseURL: '/api/goodsBasket', timeout: 3000 });
setInterceptors(apiBasket);

function getBasketList(params) {
    return apiBasket.get(`/list`, {
        params: params,
    });
}

function postBasketSave(data) {
    return apiBasket.post(`/save`, data);
}

function postBasketSaveList(data) {
    return apiBasket.post(`/saveList`, data);
}

function deleteBasket(goodsBasketSeq) {
    return apiBasket.delete(`/${goodsBasketSeq}`);
}

export { getBasketList, postBasketSave, postBasketSaveList, deleteBasket };
