import Vue from 'vue'
import App from './App.vue'
import router from './router'
import VueLodash from 'vue-lodash'
import lodash from 'lodash'
// import velocity from 'velocity-animate'
import axios from './axios'

Vue.prototype.$axios = axios;
Vue.use(VueLodash, { name: 'custom' , lodash: lodash });
// Vue.use(velocity);

Vue.config.productionTip = false;

new Vue({
	router,
	render: h => h(App),
}).$mount('#app');

