import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/test',
            component: () => import('@/views/test'),
            meta: { layout: 'clean' },
        },
        {
            path: '/',
            component: () => import('@/views/main.vue'),
            meta: { layout: 'default', aside: 'default' },
        },
        {
            path: '/asset',
            component: () => import('@/views/asset'),
            meta: { layout: 'default', aside: 'order' },
        },
        {
            path: '/login',
            component: () => import('@/views/login'),
            meta: { layout: 'clean', aside: 'order' },
        },
        {
            path: '*',
            component: { template: '<div>Not Found</div>' },
            meta: { layout: 'clean' },
        },
    ],
});

export default router;
