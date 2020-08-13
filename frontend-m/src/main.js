import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import VModal from 'vue-js-modal';

Vue.config.productionTip = false;

Vue.use(require('vue-moment'));
Vue.use(VModal, {
    dynamicDefaults: {
        width: '100%',
        height: 'auto',
        adaptive: false,
        draggable: false,
        scrollable: true,
        reset: true,
        dynamic: true,
    },
});
new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');
