import axios from 'axios';
import { setInterceptors } from './config/interceptors';

function createWithAuth(url, options) {
    const instance = axios.create(Object.assign({ baseURL: url }, options));
    setInterceptors(instance);
    return instance;
}

export const myPage = createWithAuth(`/api/mypage`, { timeout: 3000 });
