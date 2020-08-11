import store from '@/store/index.js';
import { getAuthFromCookie } from '@/utils/cookies.js';

function setInterceptors(instance) {
    instance.interceptors.request.use(
        (config) => {
            config.headers.Authorization =
                'Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJyZHMiOiJhdXRoczp5dGg1OTE0MzE0OCIsInN1YiI6Inl0aCIsImV4cCI6MTYyNjc2NTk0MywiaWF0IjoxNTk1MjI5OTQzfQ.wg_VqONs3LYXKKHclCwYSHn0jyEv6jM13TMUqKp0vLo_Mhxp0Gwj-PWWFxNhGzsXQKhryIpGV85hXHX7t-DjVA'
                // store.getters['userToken'] || getAuthFromCookie();
            return config;
        },

        (error) => Promise.reject(error.response)
    );
    instance.interceptors.response.use(
        (config) => config,
        (error) => {
            if (error.response.data.status === 401) {
                //
                this.$router.push('/login');
            }
            return Promise.reject(error.response);
        }
    );
    return instance;
}

export { setInterceptors };
