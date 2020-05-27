import Vue from 'vue'
import VueRouter from 'vue-router'
import todo from '../views/todo.vue'

Vue.use(VueRouter)

const Home = { template: '<div>Home</div>'}
const NotFound = { template: '<div>Not Found</div>'}

export default new VueRouter({
	mode: 'history',
	routes: [
		{ path: '/', component: Home},
		{ path: '/todo', component: todo},
		{ path: '/logout', component: { template: '<div>Logout</div>' }},
		{ path: '/posts',
			component: { template: '<div>Posts<router-view></router-view></div>' },
			children: [
				{ path: 'new', component: { template: '<div>New Post</div>' }},
				{ path: 'detail', component: { template: '<div>Post Detail</div>' }},
			]
		},
		{ path: '*', component: NotFound }
	]
})
