import axios from 'axios';
axios.defaults.baseURL =
    process.env.NODE_ENV === 'development'
        ? '/'
        : 'https://devapi.nikespace.co.kr';
import { setInterceptors } from './config/interceptors';

function createWithAuth(url, options) {
    console.log(`${process.env.VUE_APP_API_URL}`);
    const instance = axios.create(Object.assign({ baseURL: url }, options));
    setInterceptors(instance);
    return instance;
}

export const agency = createWithAuth('/api/agency', { timeout: 3000 });
export const auth = createWithAuth('/api/auth', { timeout: 3000 });
export const basket = createWithAuth('/api/goodsBasket', { timeout: 3000 });
export const contents = createWithAuth(`/api/contents`, { timeout: 3000 });
export const customer = createWithAuth(`/api/customer`, { timeout: 3000 });
export const myOrder = createWithAuth(`/api`, { timeout: 60000 });
export const myPage = createWithAuth(`/api/mypage`, { timeout: 3000 });
export const menu = createWithAuth(`/api/menu`, { timeout: 3000 });
export const product = createWithAuth(`/api/product`, { timeout: 3000 });
export const report = createWithAuth(`/api/report`, { timeout: 3000 });
export const wishList = createWithAuth(`/api`, { timeout: 3000 });
export const code = createWithAuth(`/api/open/code`, { timeout: 3000 });
export const user = createWithAuth(`/api/user`, { timeout: 3000 });
export const calendar = createWithAuth(`/api/calendar`, { timeout: 3000 });
export const file = createWithAuth(`/api/open`);
export const alarm = createWithAuth(`/api/alarm`, { timeout: 3000 });
export const banner = createWithAuth(`/api/banner`, { timeout: 3000 });
export const main = createWithAuth(`/api/main`, { timeout: 3000 });
