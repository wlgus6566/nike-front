import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/test',
            component: () => import('@/views/test'),
            meta: { layout: 'Clean' },
        },
        {
            path: '/',
            component: () => import('@/views/main.vue'),
            meta: { layout: 'Default', aside: 'Default' },
        },
        {
            path: '/asset',
            component: () => import('@/views/asset'),
            meta: { layout: 'Default', aside: 'Order' },
        },
        {
            path: '/login',
            component: () => import('@/views/login'),
            meta: { layout: 'Clean', aside: 'Order' },
        },
        {
            path: '*',
            component: { template: '<div>Not Found</div>' },
            meta: { layout: 'Clean' },
        },
    ],
});

export default router;
