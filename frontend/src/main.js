import Vue from 'vue';
import App from './App.vue';
import router from './router';
import axios from './axios';
import anime from 'animejs/lib/anime.es.js';

Vue.prototype.$axios = axios;

Vue.use(anime);

Vue.config.productionTip = false;

new Vue({
    router,
    render: (h) => h(App),
}).$mount('#app');
