import axios from 'axios';
import { setInterceptors } from './config/interceptors';

function createWithAuth(url, options) {
    const instance = axios.create(
        Object.assign({ baseURL: process.env.VUE_APP_API_URL + url }, options)
    );
    setInterceptors(instance);
    return instance;
}
export const agency = createWithAuth('/api/agency', { timeout: 30000 });
export const auth = createWithAuth('/api/auth', { timeout: 30000 });
export const basket = createWithAuth('/api/goodsBasket', { timeout: 30000 });
export const contents = createWithAuth(`/api/contents`, { timeout: 30000 });
export const customer = createWithAuth(`/api/customer`, { timeout: 30000 });
export const myOrder = createWithAuth(`/api`, { timeout: 60000 });
export const myPage = createWithAuth(`/api/mypage`, { timeout: 30000 });
export const menu = createWithAuth(`/api/menu`, { timeout: 30000 });
export const product = createWithAuth(`/api/product`, { timeout: 30000 });
export const report = createWithAuth(`/api/report`, { timeout: 30000 });
export const wishList = createWithAuth(`/api`, { timeout: 30000 });
export const code = createWithAuth(`/api/open/code`, { timeout: 30000 });
export const user = createWithAuth(`/api/user`, { timeout: 30000 });
export const calendar = createWithAuth(`/api/calendar`, { timeout: 30000 });
export const file = createWithAuth(`/api/open`);
export const alarm = createWithAuth(`/api/alarm`, { timeout: 30000 });
export const banner = createWithAuth(`/api/banner`, { timeout: 30000 });
export const main = createWithAuth(`/api/main`, { timeout: 30000 });
