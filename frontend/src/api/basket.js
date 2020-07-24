import axios from 'axios';
import { setInterceptors } from '@/api/config/interceptors';

const apiBasket = axios.create({ baseURL: '/api/goodsBasket', timeout: 3000 });
setInterceptors(apiBasket);

function getBasketList() {
    return apiBasket.get(`/list`);
}

function postBasketSave(data) {
    return apiBasket.post(`/save`, data);
}

function postBasketSaveList(data) {
    return apiBasket.post(`/saveList`, data);
}

function deleteBasket(seq) {
    return apiBasket.delete(`/${seq}`);
}

export { getBasketList, postBasketSave, postBasketSaveList, deleteBasket };
