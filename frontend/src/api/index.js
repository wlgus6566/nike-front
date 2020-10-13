import axios from 'axios';
import { setInterceptors } from './config/interceptors';

function createWithAuth(url, options, sessionUpdate) {
    const instance = axios.create(
        Object.assign({ baseURL: process.env.VUE_APP_API_URL + url }, options)
    );
    setInterceptors(instance, sessionUpdate);
    return instance;
}

export const agency = createWithAuth('/api/agency', { timeout: 0 }, true);
export const auth = createWithAuth('/api/auth', { timeout: 0 }, true);
export const basket = createWithAuth('/api/goodsBasket', { timeout: 0 }, true);
export const contents = createWithAuth(
    `/api/contents`,
    {
        timeout: 0,
    },
    true
);
export const customer = createWithAuth(`/api/customer`, { timeout: 0 }, true);
export const myOrder = createWithAuth(`/api`, { timeout: 0 }, true);
export const myPage = createWithAuth(`/api/mypage`, { timeout: 0 }, true);
export const menu = createWithAuth(`/api/menu`, { timeout: 0 }, true);
export const product = createWithAuth(`/api/product`, { timeout: 0 }, true);
export const report = createWithAuth(`/api/report`, { timeout: 0 }, true);
export const wishList = createWithAuth(`/api`, { timeout: 0 }, true);
export const code = createWithAuth(`/api/open/code`, { timeout: 0 }, true);
export const user = createWithAuth(`/api/user`, { timeout: 0 }, true);
export const calendar = createWithAuth(`/api/calendar`, { timeout: 0 }, true);
export const file = createWithAuth(`/api/open`, { timeout: 0 }, true);
export const alarm = createWithAuth(`/api/alarm`, { timeout: 0 }, true);
export const banner = createWithAuth(`/api/banner`, { timeout: 0 }, true);
export const main = createWithAuth(`/api/main`, { timeout: 0 }, true);
export const join = createWithAuth(`/api/join`, { timeout: 0 }, false);
export const log = createWithAuth(`/api/log`, { timeout: 0 }, false);
