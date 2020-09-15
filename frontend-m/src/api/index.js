import axios from 'axios';
import { setInterceptors } from './config/interceptors';

function createWithAuth(url, options, sessionUpdate) {
    const instance = axios.create(
        Object.assign({ baseURL: process.env.VUE_APP_API_URL + url }, options)
    );
    setInterceptors(instance, sessionUpdate);
    return instance;
}

export const myPage = createWithAuth(`/api/mypage`, { timeout: 0 }, true);
export const report = createWithAuth(`/api/report`, { timeout: 0 }, true);
export const agency = createWithAuth(`/api/agency`, { timeout: 0 }, true);
export const auth = createWithAuth('/api/auth', { timeout: 0 }, true);
export const code = createWithAuth(`/api/open/code`, { timeout: 0 }, true);
export const main = createWithAuth(`/api/main`, { timeout: 0 }, true);
export const alarm = createWithAuth(`/api/alarm`, { timeout: 0 }, true);
export const calendar = createWithAuth(`/api/calendar`, { timeout: 0 }, true);
export const contents = createWithAuth(`/api/contents`, { timeout: 0 }, true);
export const file = createWithAuth(`/api/open`);
