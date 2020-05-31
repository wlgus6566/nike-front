import Vue from 'vue'
import VueRouter from 'vue-router'
import test from '../views/test'
import main from '../views/main.vue'
import asset from '../views/asset.vue'

Vue.use(VueRouter);

const router =  new VueRouter({
	mode: 'history',
	routes: [
		{path: '/test', component: test},
		{path: '/', component: main},
		{path: '/asset', component: asset},
		{path: '*', component: {template: '<div>Not Found</div>'}}
	]
});

/*router.afterEach((to, from) => {

});*/

export default router
