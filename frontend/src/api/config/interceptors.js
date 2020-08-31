//import store from '@/store/index.js';
import router from '@/router';
import { updateCookie, getAuthFromCookie } from '@/utils/cookies.js';

function setInterceptors(instance) {
    instance.interceptors.request.use(
        (config) => {
            config.headers.Authorization = getAuthFromCookie();
            updateCookie();
            return config;
        },
        (error) => {
            console.error(error);
            return Promise.reject(error.response);
        }
    );
    instance.interceptors.response.use(
        (config) => config,
        (error) => {
            if (error.response.status === 401) {
                router.push('/login');
            }
            return Promise.reject(error.response);
        }
    );
    return instance;
}

export { setInterceptors };
