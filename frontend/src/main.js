import '@babel/polyfill';
import 'es6-promise/auto';

import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import VueCookies from 'vue-cookies';
import CKEditor from 'ckeditor4-vue';
import { setupCalendar } from 'v-calendar';
import VCalendar from 'v-calendar';

import lineClamp from './utils/lineclamp';

Vue.config.productionTip = false;

Vue.use(CKEditor);
Vue.use(VueCookies);
Vue.use(require('vue-moment'));
Vue.use(VCalendar, {
    componentPrefix: 'vc',
    locales: {
        masks: {
            title: 'YYYY.MM',
            weekdays: 'WWW',
        },
    },
});

Vue.directive('lineClamp', lineClamp);

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
    const formatted = value
        .toFixed(0)
        .replace(/(\d)(?=(\d{3})+(?:\.\d+)?$)/g, '$1,');
    return `${prefix} ${formatted} ${suffix}`;
});

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');

setupCalendar({
    componentPrefix: 'vc',
});
