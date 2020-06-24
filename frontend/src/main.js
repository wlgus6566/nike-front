import '@babel/polyfill';

import Vue from 'vue';
import App from './App.vue';
import router from './router';
import axios from './axios';

Vue.prototype.$axios = axios;

Vue.config.productionTip = false;

/**
 * Thousands Separators 천단위 ',' 숫자 표시 [이소정]
 * value : 값 (값이 없을 경우 기본 0 반환)
 * prefix : 값 앞에 붙일 표시 (없을 경우 '')
 * suffix : 값 뒤에 붙일 표시 (없을 경우 '')
 * ex) 100000, ￦, '' ->  ￦100,000
 */
Vue.filter('formattedNumber', (value, prefix, suffix) => {
    console.log('filter');
    if (!value || value.isNaN) {
        return `${prefix} 0 ${suffix}`;
    }
    const formatted = value.toFixed(0).replace(/(\d)(?=(\d{3})+(?:\.\d+)?$)/g, '$1,');
    return `${prefix} ${formatted} ${suffix}`;
});

new Vue({
    router,
    render: (h) => h(App),
}).$mount('#app');
