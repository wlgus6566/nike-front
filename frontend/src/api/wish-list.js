import axios from 'axios';
import { setInterceptors } from '@/api/config/interceptors';

const apiWishList = axios.create({ baseURL: '/api/wishlist', timeout: 3000 });
setInterceptors(apiWishList);

function getWishList(params) {
    return apiWishList.get(`/list`, {
        params: params,
    });
}
function postWishList(params) {
    return apiWishList.post(`/save`, {
        params: params,
    });
}
function deleteWishList(seq) {
    return apiWishList.delete(`/delete/${seq}`);
}
function deleteWishListAll(params) {
    return apiWishList.delete(`/delete`, {
        params: params,
    });
}

export { getWishList, postWishList, deleteWishList, deleteWishListAll };
