import store from '@/store/index.js';
import router from '@/router';
import { getAuthFromCookie } from '@/utils/cookies.js';

function setInterceptors(instance) {
    instance.interceptors.request.use(
        (config) => {
            config.headers.Authorization =
                store.getters['userToken'] || getAuthFromCookie();
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
            console.log(error);
            if (error.response.status === 401) {
                router.push('/login');
            }
            if (error.response.data.code === 'NO_AUTH') {
                //router.go(-1);
            }
            if (error.response.data.existMsg) {
                //alert(error.response.data.msg);
            }
            return Promise.reject(error.response);
        }
    );
    return instance;
}

export { setInterceptors };
