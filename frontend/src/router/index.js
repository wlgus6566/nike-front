import Vue from 'vue';
import VueRouter from 'vue-router';

// Routes
import AssetRoutes from './asset';
import ToolkitRoutes from './toolkit';

// Import methods
import { pages } from '@/util/global-methods';
Vue.use(VueRouter);

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/login',
            component: pages('login'),
            meta: { layout: 'Clean' },
        },
        {
            path: '/',
            component: pages('MainPage.vue'),
            meta: { layout: 'Default', aside: 'Default' },
        },
        ...AssetRoutes,
        ...ToolkitRoutes,
    ],
});

export default router;
