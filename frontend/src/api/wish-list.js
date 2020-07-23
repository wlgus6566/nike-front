import axios from 'axios';
import { setInterceptors } from '@/api/config/interceptors';

const apiWishList = axios.create({ baseURL: '/api/wishlist', timeout: 3000 });
setInterceptors(apiWishList);

function getWishList(params) {
    return apiWishList.get(`/list`, {
        params: params,
    });
}
export { getWishList };
