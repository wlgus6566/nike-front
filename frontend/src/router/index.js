import Vue from 'vue'
import VueRouter from 'vue-router'
import test from '@/views/test'
import main from '@/views/main.vue'
import asset from '@/views/asset'

Vue.use(VueRouter)

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/test',
            component: test,
            meta: { layout: 'default' },
        },
        {
            path: '/',
            component: main,
            meta: { layout: 'clean' },
        },
        {
            path: '/asset',
            component: asset,
            meta: { layout: 'default', aside: 'order' },
        },
        {
            path: '*',
            component: { template: '<div>Not Found</div>' },
            meta: { layout: 'clean' },
        },
    ],
})

/*router.afterEach((to, from) => {

});*/

// router.beforeEach((to, from, next) => {
// 	if (to.matched.some(record => record.meta.requiresAuth)) {
// 		// 이 라우트는 인증이 필요하며 로그인 한 경우 확인하십시오.
// 		// 그렇지 않은 경우 로그인 페이지로 리디렉션하십시오.
// 		if (!auth.loggedIn()) {
// 			next({
// 				path: '/login',
// 				query: {redirect: to.fullPath}
// 			})
// 		} else {
// 			next()
// 		}
// 	} else {
// 		next() // 반드시 next()를 호출하십시오!
// 	}
// 	next()
// })

export default router
