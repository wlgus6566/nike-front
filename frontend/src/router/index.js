import Vue from 'vue'
import VueRouter from 'vue-router'
import main from '../views/main.vue'
import asset from '../views/asset.vue'

Vue.use(VueRouter)

export default new VueRouter({
	mode: 'history',
	routes: [
		{path: '/', component: main},
		{path: '/main', component: main},
		{path: '/asset', component: asset},
		{path: '*', component: {template: '<div>Not Found</div>'}}
	]
})
