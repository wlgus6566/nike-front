import axios from 'axios';
import { setInterceptors } from './config/interceptors';

function createWithAuth(url, options) {
    const instance = axios.create(Object.assign({ baseURL: url }, options));
    setInterceptors(instance);
    return instance;
}

export const myPage = createWithAuth(`/api/mypage`, { timeout: 3000 });
export const report = createWithAuth(`/api/report`, {timeout: 3000});
export const agency = createWithAuth(`/api/agency`, { timeout: 3000 });
export const auth = createWithAuth('/api/auth', { timeout: 3000 });
export const code = createWithAuth(`/api/open/code`, { timeout: 3000 });
export const main = createWithAuth(`/api/main`, {timeout: 3000});
export const alarm = createWithAuth(`/api/alarm`, { timeout: 3000 });
