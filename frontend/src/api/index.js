import axios from 'axios';
import { setInterceptors } from './config/interceptors';

function createWithAuth(url, options) {
    const instance = axios.create(Object.assign({ baseURL: url }, options));
    setInterceptors(instance);
    return instance;
}

export const agency = createWithAuth('/api/agency', { timeout: 3000 });
export const auth = createWithAuth('/api/auth', { timeout: 3000 });
export const basket = createWithAuth('/api/goodsBasket', { timeout: 3000 });
export const contents = createWithAuth(`/api/contents`, { timeout: 3000 });
export const customer = createWithAuth(`/api/customer`, { timeout: 3000 });
export const myOrder = createWithAuth(`/api/order`, { timeout: 3000 });
export const myPage = createWithAuth(`/api/mypage`, { timeout: 3000 });
export const product = createWithAuth(`/api/product`, { timeout: 3000 });
export const report = createWithAuth(`/api/report`, { timeout: 3000 });
export const wishList = createWithAuth(`/api/wishlist`, { timeout: 3000 });
