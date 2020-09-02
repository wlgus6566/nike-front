import store from '@/store/index.js';
import router from '@/router';
import { updateCookie, getAuthFromCookie } from '@/utils/cookies.js';

function setInterceptors(instance) {
    instance.interceptors.request.use(
        (config) => {
            config.headers.Authorization = getAuthFromCookie();
            return config;
        },
        (error) => {
            console.error(error);
            return Promise.reject(error.response);
        }
    );
    instance.interceptors.response.use(
        (config) => {
            updateCookie();
            store.commit('SET_LOGOUT_TIMER');
            //logoutCount(new Date());
            return config;
        },
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
