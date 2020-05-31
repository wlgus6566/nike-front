import Vue from 'vue'
import App from './App.vue'
import router from './router';
import lodash from 'lodash'

Vue.config.productionTip = false

new Vue({
  router,
  lodash,
  render: h => h(App),
}).$mount('#app')
